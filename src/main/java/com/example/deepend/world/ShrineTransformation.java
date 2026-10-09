package com.example.deepend.world;

import com.example.deepend.block.ObservationShrineBlock;
import com.example.deepend.block.ShrineCollisionBlock;
import com.example.deepend.registry.DeepEndBlocks;
import com.example.deepend.entity.AncientShrineEntity;
import com.example.deepend.registry.DeepEndEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.phys.AABB;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import java.util.LinkedHashMap;

/** Validates the exact recipe before swapping it for one persistent display model. */
public final class ShrineTransformation {
    public static final float MODEL_SCALE = 18.0F;
    private ShrineTransformation() {}
    public record Result(boolean success, String message) {}

    public static BlockPos baseFromPedestal(BlockPos pedestal) { return pedestal.offset(0, -3, 3); }
    private static String tag(BlockPos base) { return "deep_end_shrine_" + base.getX() + "_" + base.getY() + "_" + base.getZ(); }
    private static AABB bounds(BlockPos base) {
        return new AABB(base.getX() - 8, base.getY() - 1, base.getZ() - 8,
                base.getX() + 9, base.getY() + 19, base.getZ() + 9);
    }
    private static boolean loaded(ServerLevel level, BlockPos base) {
        if (base.getY() < level.getMinY() || base.getY() + ShrineLayout.HEIGHT > level.getMaxY()) return false;
        for (int x = -7; x <= 7; x++)
            for (int z = -7; z <= 7; z++)
                if (!level.hasChunkAt(base.offset(x, 0, z))) return false;
        return true;
    }

    private static java.util.List<Display.BlockDisplay> legacyDisplays(ServerLevel level, BlockPos base) {
        return level.getEntitiesOfClass(Display.BlockDisplay.class, bounds(base),
                entity -> entity.entityTags().contains(tag(base)));
    }

    public static Result awaken(ServerLevel level, BlockPos pedestal) {
        BlockPos base = baseFromPedestal(pedestal);
        if (!loaded(level, base)) return new Result(false, "The complete shrine area must be loaded and inside world height.");
        if (level.getBlockState(pedestal).getValue(ObservationShrineBlock.AWAKENED)) {
            var legacy = legacyDisplays(level, base);
            var current = level.getEntitiesOfClass(AncientShrineEntity.class, bounds(base),
                    entity -> entity.entityTags().contains(tag(base)));
            if (current.isEmpty() && !legacy.isEmpty()) {
                legacy.forEach(Display.BlockDisplay::discard);
                AncientShrineEntity migrated = createDisplay(level, base);
                if (level.addFreshEntity(migrated))
                    return new Result(true, "The shrine visual was upgraded to the custom model.");
                return new Result(false, "The custom shrine model could not spawn; nothing was consumed.");
            }
            return new Result(false, "This shrine is already awakened. Sneak-right-click with a Crystal to restore its block build.");
        }
        var plan = ShrineLayout.create();
        var before = new LinkedHashMap<BlockPos, BlockState>();
        var mismatch = ShrineRecipe.firstMismatch(plan, (local, material) -> {
            BlockPos pos = base.offset(local.x(), local.y(), local.z());
            BlockState actual = level.getBlockState(pos);
            if (material == null) return actual.isAir();
            if (!actual.equals(ObservationShrineStructure.stateAt(local, material))) return false;
            before.put(pos, actual);
            return true;
        });
        if (mismatch.isPresent()) {
            var local = mismatch.get();
            BlockPos pos = base.offset(local.x(), local.y(), local.z());
            return new Result(false, "The shrine build is incomplete or obstructed at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + ". No Crystal consumed.");
        }
        // Register the display before changing the recipe, so a cancelled entity spawn is harmless.
        AncientShrineEntity display = createDisplay(level, base);
        if (!level.addFreshEntity(display)) return new Result(false, "The shrine model could not spawn. Nothing was changed.");
        if (ShrineRecipe.firstMismatch(plan, (local, material) -> {
            BlockState actual = level.getBlockState(base.offset(local.x(), local.y(), local.z()));
            return material == null ? actual.isAir() : actual.equals(ObservationShrineStructure.stateAt(local, material));
        }).isPresent()) {
            display.discard();
            return new Result(false, "The shrine changed during activation. Nothing was consumed.");
        }
        try {
            for (var entry : plan.entrySet()) {
                var p = entry.getKey();
                BlockPos pos = base.offset(p.x(), p.y(), p.z());
                BlockState replacement = switch (entry.getValue()) {
                    case PEDESTAL -> DeepEndBlocks.OBSERVATION_SHRINE.get().defaultBlockState().setValue(ObservationShrineBlock.AWAKENED, true);
                    case FOUNDATION, DECK, TOWER, RUNE, STEP -> DeepEndBlocks.SHRINE_COLLISION.get().defaultBlockState()
                            .setValue(ShrineCollisionBlock.HALF, entry.getValue() == ShrineLayout.Material.STEP);
                    default -> Blocks.AIR.defaultBlockState();
                };
                level.setBlock(pos, replacement, 3);
                if (!level.getBlockState(pos).equals(replacement)) throw new IllegalStateException("Block replacement refused");
            }
        } catch (RuntimeException failure) {
            display.discard();
            before.forEach((pos, state) -> level.setBlock(pos, state, 3));
            return new Result(false, "Shrine transformation failed; its original blocks were restored.");
        }
        return new Result(true, "The complete shrine awakens into its ancient form.");
    }

    private static AncientShrineEntity createDisplay(ServerLevel level, BlockPos base) {
        var display = new AncientShrineEntity(DeepEndEntities.ANCIENT_SHRINE.get(), level);
        display.setPos(base.getX() + 0.5D, base.getY(), base.getZ() + 0.5D);
        display.addTag(tag(base));
        return display;
    }

    /** Restore only owned collision cells or empty recipe cells; never overwrite foreign blocks. */
    public static Result restore(ServerLevel level, BlockPos pedestal, boolean restorePedestal) {
        BlockPos base = baseFromPedestal(pedestal);
        if (!loaded(level, base)) return new Result(false, "Load the complete shrine area before restoring it.");
        var displays = level.getEntitiesOfClass(AncientShrineEntity.class, bounds(base),
                entity -> entity.entityTags().contains(tag(base)));
        var legacy = legacyDisplays(level, base);
        boolean active = level.getBlockState(pedestal).is(DeepEndBlocks.OBSERVATION_SHRINE.get())
                && level.getBlockState(pedestal).getValue(ObservationShrineBlock.AWAKENED);
        if (displays.isEmpty() && legacy.isEmpty() && !active) return new Result(false, "This shrine has not been transformed.");
        if (restorePedestal) {
            for (var entry : ShrineLayout.create().entrySet()) {
                if (entry.getValue() == ShrineLayout.Material.PEDESTAL) continue;
                var p = entry.getKey();
                BlockPos pos = base.offset(p.x(), p.y(), p.z());
                if (!level.getBlockState(pos).isAir() && !level.getBlockState(pos).is(DeepEndBlocks.SHRINE_COLLISION.get()))
                    return new Result(false, "Remove the added block at " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + " before restoring. Nothing changed.");
            }
        }
        displays.forEach(AncientShrineEntity::discard);
        legacy.forEach(Display.BlockDisplay::discard);
        for (var entry : ShrineLayout.create().entrySet()) {
            var p = entry.getKey();
            BlockPos pos = base.offset(p.x(), p.y(), p.z());
            if (entry.getValue() == ShrineLayout.Material.PEDESTAL) {
                if (restorePedestal && level.getBlockState(pos).is(DeepEndBlocks.OBSERVATION_SHRINE.get()))
                    level.setBlock(pos, ObservationShrineStructure.stateAt(p, entry.getValue()), 3);
            } else if (level.getBlockState(pos).is(DeepEndBlocks.SHRINE_COLLISION.get()) || level.getBlockState(pos).isAir())
                level.setBlock(pos, ObservationShrineStructure.stateAt(p, entry.getValue()), 3);
        }
        return new Result(true, "The shrine returns to its repairable block build. No Crystal consumed.");
    }
}
