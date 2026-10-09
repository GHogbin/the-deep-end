package com.example.deepend.world;

import com.example.deepend.registry.DeepEndBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import java.util.LinkedHashMap;
import java.util.Map;

/** A hand-built landmark prototype. Placement refuses to overwrite occupied space. */
public final class ObservationShrineStructure {
    private ObservationShrineStructure() {}

    public static boolean place(ServerLevel level, BlockPos base) {
        Map<BlockPos, BlockState> plan = new LinkedHashMap<>();
        // Stepped obsidian plinth, nine blocks wide.
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                if (Math.abs(x) == 4 && Math.abs(z) == 4) continue;
                plan.put(base.offset(x, 0, z), Blocks.OBSIDIAN.defaultBlockState());
                if (Math.abs(x) <= 3 && Math.abs(z) <= 3)
                    plan.put(base.offset(x, 1, z), Blocks.POLISHED_BLACKSTONE.defaultBlockState());
            }
        }
        // Twin rune towers and shorter surrounding spires.
        for (int x : new int[]{-3, 3}) {
            for (int y = 2; y <= 11; y++) {
                plan.put(base.offset(x, y, 1), (y % 3 == 0
                        ? DeepEndBlocks.RESONANT_CRYSTAL_BLOCK.get()
                        : Blocks.CRYING_OBSIDIAN).defaultBlockState());
                if (y <= 9) plan.put(base.offset(x, y, 2), Blocks.POLISHED_BLACKSTONE.defaultBlockState());
            }
            plan.put(base.offset(x, 12, 1), Blocks.AMETHYST_BLOCK.defaultBlockState());
            for (int z : new int[]{-3, 3}) {
                for (int y = 2; y <= 5; y++)
                    plan.put(base.offset(x, y, z), Blocks.CRYING_OBSIDIAN.defaultBlockState());
                plan.put(base.offset(x, 6, z), DeepEndBlocks.RESONANT_CRYSTAL_BLOCK.get().defaultBlockState());
            }
        }
        // Broken geometric ring around a suspended core.
        for (int x = -2; x <= 2; x++) {
            for (int y = 4; y <= 8; y++) {
                int d = Math.abs(x) + Math.abs(y - 6);
                if (d == 2) plan.put(base.offset(x, y, 0), Blocks.CRYING_OBSIDIAN.defaultBlockState());
            }
        }
        plan.put(base.offset(0, 6, 0), DeepEndBlocks.RESONANT_CRYSTAL_BLOCK.get().defaultBlockState());
        // Reachable activation pedestal; the Lens detects this core.
        plan.put(base.offset(0, 2, -2), Blocks.POLISHED_BLACKSTONE.defaultBlockState());
        plan.put(base.offset(0, 3, -2), DeepEndBlocks.OBSERVATION_SHRINE.get().defaultBlockState());
        for (BlockPos pos : plan.keySet()) {
            if (pos.getY() < level.getMinY() || pos.getY() >= level.getMaxY()
                    || !level.hasChunkAt(pos) || !level.getBlockState(pos).isAir()) return false;
        }
        for (var entry : plan.entrySet()) level.setBlock(entry.getKey(), entry.getValue(), 3);
        return true;
    }
}
