package com.example.deepend.client;

import com.example.deepend.DeepEnd;
import com.example.deepend.registry.DeepEndEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;

public final class DeepEndClientEvents {
    private DeepEndClientEvents() {}
    public static void register() {
        EntityRenderersEvent.RegisterLayerDefinitions.BUS.addListener(DeepEndClientEvents::registerLayers);
        EntityRenderersEvent.RegisterRenderers.BUS.addListener(DeepEndClientEvents::registerRenderers);
    }
    private static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
        event.registerLayerDefinition(AncientShrineModel.STONE_LAYER, AncientShrineModel::createStone);
        event.registerLayerDefinition(AncientShrineModel.RUNE_LAYER, AncientShrineModel::createRune);
        event.registerLayerDefinition(AncientShrineModel.CRYSTAL_LAYER, AncientShrineModel::createCrystal);
    }
    private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(DeepEndEntities.ANCIENT_SHRINE.get(), AncientShrineRenderer::new);
    }
}
