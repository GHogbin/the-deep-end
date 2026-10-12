package com.example.deepend.registry;

import com.example.deepend.DeepEnd;
import com.example.deepend.item.ResonanceLensItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class DeepEndItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DeepEnd.MOD_ID);

    public static final RegistryObject<Item> RESONANT_CRYSTAL = ITEMS.register("resonant_crystal",
            () -> new Item(new Item.Properties().setId(ITEMS.key("resonant_crystal"))));
    public static final RegistryObject<Item> CHORUS_FIBRE = ITEMS.register("chorus_fibre",
            () -> new Item(new Item.Properties().setId(ITEMS.key("chorus_fibre"))));
    public static final RegistryObject<Item> RESONANCE_LENS = ITEMS.register("resonance_lens",
            () -> new ResonanceLensItem(new Item.Properties().setId(ITEMS.key("resonance_lens")).stacksTo(1).durability(16)));
    public static final RegistryObject<Item> RESONANT_CRYSTAL_BLOCK_ITEM = ITEMS.register("resonant_crystal_block",
            () -> new BlockItem(DeepEndBlocks.RESONANT_CRYSTAL_BLOCK.get(), new Item.Properties().setId(ITEMS.key("resonant_crystal_block"))));
    public static final RegistryObject<Item> OBSERVATION_SHRINE_ITEM = ITEMS.register("observation_shrine",
            () -> new BlockItem(DeepEndBlocks.OBSERVATION_SHRINE.get(), new Item.Properties().setId(ITEMS.key("observation_shrine"))));

    public static final RegistryObject<Item> SHRINE_RUNE_STONE_ITEM = ITEMS.register("shrine_rune_stone",
            () -> new BlockItem(DeepEndBlocks.SHRINE_RUNE_STONE.get(), new Item.Properties().setId(ITEMS.key("shrine_rune_stone"))));
    public static final RegistryObject<Item> SHRINE_CRYSTAL_ITEM = ITEMS.register("shrine_crystal",
            () -> new BlockItem(DeepEndBlocks.SHRINE_CRYSTAL.get(), new Item.Properties().setId(ITEMS.key("shrine_crystal"))));
    public static final RegistryObject<Item> SHRINE_FRAME_ITEM = ITEMS.register("shrine_frame",
            () -> new BlockItem(DeepEndBlocks.SHRINE_FRAME.get(), new Item.Properties().setId(ITEMS.key("shrine_frame"))));

    public static final RegistryObject<Item> ANCIENT_SHRINE_STONE_ITEM = ITEMS.register("ancient_shrine_stone",
            () -> new BlockItem(DeepEndBlocks.ANCIENT_SHRINE_STONE.get(), new Item.Properties().setId(ITEMS.key("ancient_shrine_stone"))));
    public static final RegistryObject<Item> ANCIENT_SHRINE_SLAB_ITEM = ITEMS.register("ancient_shrine_slab",
            () -> new BlockItem(DeepEndBlocks.ANCIENT_SHRINE_SLAB.get(), new Item.Properties().setId(ITEMS.key("ancient_shrine_slab"))));

    private DeepEndItems() {}
}
