package com.example.deepend.world;

import com.example.deepend.registry.DeepEndBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.level.ChunkEvent;

/** Adds sparse, deterministic visual landmarks to newly generated End regions. */
public final class RegionTerrainGeneration {
    private static final int DRAGON_ISLAND_RADIUS = 1500;

    private RegionTerrainGeneration() {}

    public static void register() {
        ChunkEvent.Load.BUS.addListener(RegionTerrainGeneration::onChunkLoad);
    }

    private static void onChunkLoad(ChunkEvent.Load event) {
        if (!event.isNewChunk()) return;

        ChunkAccess chunk = event.getChunk();
        if (!(chunk.getWorldForge() instanceof net.minecraft.server.level.ServerLevel level)
                || level.dimension() != Level.END) return;

        ChunkPos chunkPos = chunk.getPos();
        int centerX = chunkPos.getMiddleBlockX();
        int centerZ = chunkPos.getMiddleBlockZ();
        if ((long) centerX * centerX + (long) centerZ * centerZ
                < (long) DRAGON_ISLAND_RADIUS * DRAGON_ISLAND_RADIUS) return;

        EndRegion region = EndRegionSampler.sample(level, new BlockPos(centerX, 0, centerZ)).region();
        RandomSource random = RandomSource.create(seedFor(level, chunkPos));

        // One landmark at most per chunk keeps these markers rare and makes the pass cheap.
        if (random.nextInt(regionChance(region)) != 0) return;
        int localX = 3 + random.nextInt(10);
        int localZ = 3 + random.nextInt(10);
        int surfaceY = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, localX, localZ);
        BlockPos base = new BlockPos(chunkPos.getMinBlockX() + localX, surfaceY, chunkPos.getMinBlockZ() + localZ);
        if (!chunk.getBlockState(base.below()).isSolid() || !chunk.getBlockState(base).isAir()) return;

        BlockState material = materialFor(region);
        int height = heightFor(region, random);
        for (int y = 0; y < height; y++) {
            BlockPos target = base.above(y);
            if (!chunk.getBlockState(target).isAir()) return;
            chunk.setBlockState(target, material, 2);
        }
    }

    private static int regionChance(EndRegion region) {
        return switch (region) {
            case OUTER_END -> 12;
            case FRINGE -> 8;
            case DEEP_END -> 6;
            case ABYSS -> 4;
            case DRAGON_ISLAND -> Integer.MAX_VALUE;
        };
    }

    /** Places one region marker at the player's location for acceptance testing. */
    public static boolean placeTestLandmark(net.minecraft.server.level.ServerLevel level, BlockPos origin) {
        if (level.dimension() != Level.END) return false;
        EndRegion region = EndRegionSampler.sample(level, origin).region();
        if (region == EndRegion.DRAGON_ISLAND) return false;
        int surfaceY = level.getHeight(Heightmap.Types.WORLD_SURFACE, origin.getX(), origin.getZ());
        BlockPos base = new BlockPos(origin.getX(), surfaceY, origin.getZ());
        if (!level.getBlockState(base.below()).isSolid() || !level.getBlockState(base).isAir()) return false;
        int height = switch (region) {
            case OUTER_END -> 2;
            case FRINGE -> 3;
            case DEEP_END -> 4;
            case ABYSS -> 5;
            case DRAGON_ISLAND -> 0;
        };
        BlockState material = materialFor(region);
        for (int y = 0; y < height; y++) level.setBlock(base.above(y), material, 3);
        return true;
    }

    private static int heightFor(EndRegion region, RandomSource random) {
        return switch (region) {
            case OUTER_END -> 1 + random.nextInt(2);
            case FRINGE -> 1 + random.nextInt(3);
            case DEEP_END -> 2 + random.nextInt(4);
            case ABYSS -> 3 + random.nextInt(5);
            case DRAGON_ISLAND -> 0;
        };
    }

    private static BlockState materialFor(EndRegion region) {
        return switch (region) {
            case OUTER_END -> Blocks.PURPUR_BLOCK.defaultBlockState();
            case FRINGE -> DeepEndBlocks.ANCIENT_SHRINE_STONE.get().defaultBlockState();
            case DEEP_END -> DeepEndBlocks.SHRINE_RUNE_STONE.get().defaultBlockState();
            case ABYSS -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            case DRAGON_ISLAND -> Blocks.END_STONE.defaultBlockState();
        };
    }

    private static long seedFor(net.minecraft.server.level.ServerLevel level, ChunkPos pos) {
        long seed = level.getSeed();
        seed += (long) pos.x() * 341873128712L;
        seed += (long) pos.z() * 132897987541L;
        seed ^= seed >>> 33;
        seed *= 0xff51afd7ed558ccdL;
        seed ^= seed >>> 33;
        seed *= 0xc4ceb9fe1a85ec53L;
        return seed ^ (seed >>> 33);
    }
}
