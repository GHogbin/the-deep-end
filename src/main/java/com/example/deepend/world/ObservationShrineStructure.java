package com.example.deepend.world;

import com.example.deepend.registry.DeepEndBlocks;
import com.example.deepend.block.ShrineFrameBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/** Preflight the entire clearance volume before changing any blocks. */
public final class ObservationShrineStructure {
    private ObservationShrineStructure() {}

    public static boolean place(ServerLevel level, BlockPos base) {
        var plan = ShrineLayout.create();
        for (int x = -ShrineLayout.RADIUS; x <= ShrineLayout.RADIUS; x++) {
            for (int z = -ShrineLayout.RADIUS; z <= ShrineLayout.RADIUS; z++) {
                for (int y = 0; y < ShrineLayout.HEIGHT; y++) {
                    BlockPos pos = base.offset(x, y, z);
                    if (pos.getY() < level.getMinY() || pos.getY() >= level.getMaxY()
                            || !level.hasChunkAt(pos) || !level.getBlockState(pos).isAir()) return false;
                }
            }
        }
        for (var entry : plan.entrySet()) {
            var pos = entry.getKey();
            BlockState state = state(entry.getValue());
            if (entry.getValue() == ShrineLayout.Material.FRAME)
                state = state.setValue(ShrineFrameBlock.ASCENDING, (pos.x() >= 0) == (pos.y() < 9));
            level.setBlock(base.offset(pos.x(), pos.y(), pos.z()), state, 3);
        }
        return true;
    }

    private static BlockState state(ShrineLayout.Material material) {
        return switch (material) {
            case FOUNDATION -> Blocks.OBSIDIAN.defaultBlockState();
            case DECK -> Blocks.POLISHED_BLACKSTONE.defaultBlockState();
            case STEP -> Blocks.POLISHED_BLACKSTONE_SLAB.defaultBlockState();
            case TOWER -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            case RUNE -> DeepEndBlocks.SHRINE_RUNE_STONE.get().defaultBlockState();
            case CRYSTAL -> DeepEndBlocks.SHRINE_CRYSTAL.get().defaultBlockState();
            case FRAME -> DeepEndBlocks.SHRINE_FRAME.get().defaultBlockState();
            case PEDESTAL -> DeepEndBlocks.OBSERVATION_SHRINE.get().defaultBlockState();
        };
    }
}
