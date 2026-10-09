package com.example.deepend;

import com.example.deepend.registry.DeepEndItems;
import com.example.deepend.registry.DeepEndBlocks;
import com.example.deepend.registry.DeepEndEntities;
import com.example.deepend.world.RegionTerrainGeneration;
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
        DeepEndEntities.ENTITIES.register(bus);
        RegionTerrainGeneration.register();
        // The renderer event classes are client-only in 26.3. Reflection keeps the common
        // mod constructor safe on a dedicated server while registering early on a client.
        try {
            Class.forName("com.example.deepend.client.DeepEndClientEvents")
                    .getMethod("register").invoke(null);
        } catch (ReflectiveOperationException | LinkageError ignored) {
            // Dedicated servers do not load client renderer classes.
        }
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        DeepEndCommands.register(event.getDispatcher());
    }
}
