package com.example.deepend.client;

import com.example.deepend.DeepEnd;
import com.example.deepend.registry.DeepEndEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=DeepEnd.MOD_ID, bus=Mod.EventBusSubscriber.Bus.MOD, value=Dist.CLIENT)
public final class DeepEndClientEvents {
    private DeepEndClientEvents() {}
    @SubscribeEvent public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(AncientShrineModel.STONE_LAYER, AncientShrineModel::createStone);
        event.registerLayerDefinition(AncientShrineModel.RUNE_LAYER, AncientShrineModel::createRune);
        event.registerLayerDefinition(AncientShrineModel.CRYSTAL_LAYER, AncientShrineModel::createCrystal);
    }
    @SubscribeEvent public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(DeepEndEntities.ANCIENT_SHRINE.get(), AncientShrineRenderer::new);
    }
}
