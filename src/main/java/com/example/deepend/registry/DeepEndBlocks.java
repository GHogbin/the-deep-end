package com.example.deepend.registry;

import com.example.deepend.DeepEnd;
import com.example.deepend.block.ObservationShrineBlock;
import com.example.deepend.block.ShrineFrameBlock;
import com.example.deepend.block.ShrineCrystalBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class DeepEndBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, DeepEnd.MOD_ID);

    public static final RegistryObject<Block> RESONANT_CRYSTAL_BLOCK = BLOCKS.register("resonant_crystal_block",
            () -> new Block(BlockBehaviour.Properties.of().setId(BLOCKS.key("resonant_crystal_block")).mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(2.5F, 6.0F).sound(SoundType.AMETHYST).lightLevel(state -> 5)));

    public static final RegistryObject<Block> OBSERVATION_SHRINE = BLOCKS.register("observation_shrine",
            () -> new ObservationShrineBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("observation_shrine")).mapColor(MapColor.COLOR_PURPLE)
                    .strength(3.0F, 10.0F).sound(SoundType.STONE).lightLevel(state -> 3)));

    public static final RegistryObject<Block> SHRINE_RUNE_STONE = BLOCKS.register("shrine_rune_stone",
            () -> new Block(BlockBehaviour.Properties.of().setId(BLOCKS.key("shrine_rune_stone"))
                    .mapColor(MapColor.COLOR_BLACK).strength(4.0F, 20.0F).sound(SoundType.STONE)
                    .noOcclusion().lightLevel(state -> 4)));
    public static final RegistryObject<Block> SHRINE_CRYSTAL = BLOCKS.register("shrine_crystal",
            () -> new ShrineCrystalBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("shrine_crystal"))
                    .mapColor(MapColor.COLOR_PURPLE).strength(2.5F).sound(SoundType.AMETHYST)
                    .noOcclusion().noCollision().lightLevel(state -> 8)));
    public static final RegistryObject<Block> SHRINE_FRAME = BLOCKS.register("shrine_frame",
            () -> new ShrineFrameBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("shrine_frame"))
                    .mapColor(MapColor.COLOR_BLACK).strength(4.0F, 20.0F).sound(SoundType.STONE).noOcclusion()));

    public static final RegistryObject<Block> ANCIENT_SHRINE_STONE = BLOCKS.register("ancient_shrine_stone",
            () -> new Block(BlockBehaviour.Properties.of().setId(BLOCKS.key("ancient_shrine_stone"))
                    .mapColor(MapColor.COLOR_BLACK).strength(4.0F, 20.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ANCIENT_SHRINE_SLAB = BLOCKS.register("ancient_shrine_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().setId(BLOCKS.key("ancient_shrine_slab"))
                    .mapColor(MapColor.COLOR_BLACK).strength(4.0F, 20.0F).sound(SoundType.STONE)));

    private DeepEndBlocks() {}
}
