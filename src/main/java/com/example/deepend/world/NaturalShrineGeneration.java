package com.example.deepend.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

/** Deterministic, sparse landmark discovery for distant End exploration. */
public final class NaturalShrineGeneration {
    public static final int CELL_SIZE = 4096;
    private static final int MIN_DISTANCE = 8000;
    private NaturalShrineGeneration() {}

    public static void onPlayerTick(ServerPlayer player) {
        if (player.tickCount % 20 != 0 || player.level().dimension() != Level.END) return;
        double distance = Math.sqrt((double) player.getBlockX() * player.getBlockX()
                + (double) player.getBlockZ() * player.getBlockZ());
        if (distance < MIN_DISTANCE) return;
        int cellX = Math.floorDiv(player.getBlockX(), CELL_SIZE);
        int cellZ = Math.floorDiv(player.getBlockZ(), CELL_SIZE);
        long seed = mix(cellX * 341873128712L + cellZ * 132897987541L);
        // Three out of four grid cells are intentionally silent; the fourth has a landmark.
        if ((seed & 3L) != 0L) return;
        int candidateX = cellX * CELL_SIZE + 1024 + (int)((seed >>> 8) & 2047);
        int candidateZ = cellZ * CELL_SIZE + 1024 + (int)((seed >>> 20) & 2047);
        if (player.distanceToSqr(candidateX + .5D, player.getY(), candidateZ + .5D) > 192D * 192D) return;
        ServerLevel level = (ServerLevel) player.level();
        int surface = level.getHeight(Heightmap.Types.WORLD_SURFACE, candidateX, candidateZ);
        BlockPos base = new BlockPos(candidateX, surface + 1, candidateZ);
        if (surface <= level.getMinY() || level.getBlockState(base.below()).isAir()) return;
        if (ObservationShrineStructure.placeSingle(level, base)) player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                "The Resonance Lens stirs: an ancient shrine has surfaced nearby."));
    }

    public static long mix(long value) {
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        return value ^ (value >>> 33);
    }
}
