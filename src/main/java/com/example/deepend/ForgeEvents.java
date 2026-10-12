package com.example.deepend;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DeepEnd.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeEvents {
    private ForgeEvents() {}
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        DeepEnd.registerCommands(event);
    }
    @SubscribeEvent
    public static void naturalShrine(TickEvent.PlayerTickEvent.Post event) {
        if (event.player() instanceof net.minecraft.server.level.ServerPlayer player)
            com.example.deepend.world.NaturalShrineGeneration.onPlayerTick(player);
    }
}
