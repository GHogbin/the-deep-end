package com.example.deepend.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public final class EndRegionSampler {
    private EndRegionSampler() {}

    public static EndRegionSample sample(ServerLevel level, BlockPos pos) {
        if (level.dimension() != Level.END) return new EndRegionSample(0, EndRegion.OUTER_END);
        double distance = Math.sqrt((double) pos.getX() * pos.getX() + (double) pos.getZ() * pos.getZ());
        EndRegion region = distance < 1500 ? EndRegion.DRAGON_ISLAND
                : distance < 8000 ? EndRegion.OUTER_END
                : distance < 20000 ? EndRegion.FRINGE
                : distance < 50000 ? EndRegion.DEEP_END : EndRegion.ABYSS;
        return new EndRegionSample(distance, region);
    }
}

