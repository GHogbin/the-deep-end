package com.example.deepend.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

/** Deterministic, sparse landmark discovery for distant End exploration. */
public final class NaturalShrineGeneration {
    public static final int CELL_SIZE = 2048;
    private static final int MIN_DISTANCE = 8000;
    private NaturalShrineGeneration() {}

    public static void onPlayerTick(ServerPlayer player) {
        if (player.tickCount % 20 != 0 || player.level().dimension() != Level.END) return;
        double distance = Math.sqrt((double) player.getBlockX() * player.getBlockX()
                + (double) player.getBlockZ() * player.getBlockZ());
        if (distance < MIN_DISTANCE) return;
        int cellX = Math.floorDiv(player.getBlockX(), CELL_SIZE);
        int cellZ = Math.floorDiv(player.getBlockZ(), CELL_SIZE);
        BlockPos candidate = candidateForCell(cellX, cellZ);
        if (player.distanceToSqr(candidate.getX() + .5D, player.getY(), candidate.getZ() + .5D) > 768D * 768D) return;
        ServerLevel level = (ServerLevel) player.level();
        int surface = level.getHeight(Heightmap.Types.WORLD_SURFACE, candidate.getX(), candidate.getZ());
        BlockPos base = new BlockPos(candidate.getX(), surface + 1, candidate.getZ());
        if (surface <= level.getMinY() || level.getBlockState(base.below()).isAir()) return;
        if (ObservationShrineStructure.placeSingle(level, base)) player.sendSystemMessage(net.minecraft.network.chat.Component.literal(
                "The Resonance Lens stirs: an ancient shrine has surfaced nearby."));
    }

    public static BlockPos candidateForCell(int cellX, int cellZ) {
        long seed = mix(cellX * 341873128712L + cellZ * 132897987541L);
        return new BlockPos(cellX * CELL_SIZE + 512 + (int)((seed >>> 8) & 1023), 0,
                cellZ * CELL_SIZE + 512 + (int)((seed >>> 20) & 1023));
    }

    public static BlockPos nearestCandidate(BlockPos origin) {
        BlockPos nearest = null;
        double distance = Double.MAX_VALUE;
        int cellX = Math.floorDiv(origin.getX(), CELL_SIZE);
        int cellZ = Math.floorDiv(origin.getZ(), CELL_SIZE);
        for (int x = cellX - 2; x <= cellX + 2; x++) for (int z = cellZ - 2; z <= cellZ + 2; z++) {
            BlockPos candidate = candidateForCell(x, z);
            if (Math.hypot(candidate.getX(), candidate.getZ()) < MIN_DISTANCE) continue;
            double next = origin.distSqr(candidate);
            if (next < distance) { distance = next; nearest = candidate; }
        }
        return nearest;
    }

    public static long mix(long value) {
        value ^= value >>> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >>> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        return value ^ (value >>> 33);
    }
}
