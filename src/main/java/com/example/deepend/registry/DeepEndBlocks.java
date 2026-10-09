package com.example.deepend.registry;

import com.example.deepend.DeepEnd;
import com.example.deepend.block.ObservationShrineBlock;
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

    private DeepEndBlocks() {}
}
