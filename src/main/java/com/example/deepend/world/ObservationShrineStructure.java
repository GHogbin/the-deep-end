package com.example.deepend.world;

import com.example.deepend.registry.DeepEndBlocks;
import com.example.deepend.block.ShrineFrameBlock;
import com.example.deepend.block.ShrineCrystalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
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
            BlockState state = stateAt(pos, entry.getValue());
            level.setBlock(base.offset(pos.x(), pos.y(), pos.z()), state, 3);
        }
        return true;
    }

    public static BlockState stateAt(ShrineLayout.Position pos, ShrineLayout.Material material) {
        BlockState state = switch (material) {
            case FOUNDATION, DECK, TOWER -> DeepEndBlocks.ANCIENT_SHRINE_STONE.get().defaultBlockState();
            case STEP -> DeepEndBlocks.ANCIENT_SHRINE_SLAB.get().defaultBlockState();
            case RUNE -> DeepEndBlocks.SHRINE_RUNE_STONE.get().defaultBlockState();
            case CRYSTAL -> DeepEndBlocks.SHRINE_CRYSTAL.get().defaultBlockState();
            case LARGE_CRYSTAL -> DeepEndBlocks.SHRINE_CRYSTAL.get().defaultBlockState().setValue(ShrineCrystalBlock.LARGE, true);
            case FRAME, FRAME_FORK -> DeepEndBlocks.SHRINE_FRAME.get().defaultBlockState();
            case PEDESTAL -> DeepEndBlocks.OBSERVATION_SHRINE.get().defaultBlockState();
        };
        if (material == ShrineLayout.Material.FRAME)
            state = state.setValue(ShrineFrameBlock.ASCENDING, (pos.x() >= 0) == (pos.y() < 9));
        if (material == ShrineLayout.Material.FRAME_FORK)
            state = state.setValue(ShrineFrameBlock.FORK, true);
        return state;
    }
}
