package com.example.deepend;

import com.example.deepend.registry.DeepEndItems;
import com.example.deepend.registry.DeepEndBlocks;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DeepEnd.MOD_ID)
public final class DeepEnd {
    public static final String MOD_ID = "deep_end";

    public DeepEnd() {
        var bus = FMLJavaModLoadingContext.get().getModBusGroup();
        DeepEndItems.ITEMS.register(bus);
        DeepEndBlocks.BLOCKS.register(bus);
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        DeepEndCommands.register(event.getDispatcher());
    }
}
