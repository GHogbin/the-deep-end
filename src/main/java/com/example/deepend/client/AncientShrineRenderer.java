package com.example.deepend.client;

import com.example.deepend.entity.AncientShrineEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.vertex.PoseStack;

public final class AncientShrineRenderer extends EntityRenderer<AncientShrineEntity, EntityRenderState> {
    private static final Identifier STONE = Identifier.fromNamespaceAndPath("deep_end", "textures/block/ancient_shrine_stone.png");
    private static final Identifier RUNE = Identifier.fromNamespaceAndPath("deep_end", "textures/block/ancient_rune_inlay.png");
    private static final Identifier CRYSTAL = Identifier.fromNamespaceAndPath("deep_end", "textures/block/ancient_crystal.png");
    private final AncientShrineModel stone, rune, crystal;
    public AncientShrineRenderer(EntityRendererProvider.Context context) {
        super(context); shadowRadius=0;
        stone=new AncientShrineModel(context.bakeLayer(AncientShrineModel.STONE_LAYER));
        rune=new AncientShrineModel(context.bakeLayer(AncientShrineModel.RUNE_LAYER));
        crystal=new AncientShrineModel(context.bakeLayer(AncientShrineModel.CRYSTAL_LAYER));
    }
    @Override public EntityRenderState createRenderState(){return new EntityRenderState();}
    @Override public void submit(EntityRenderState state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera){
        pose.pushPose(); pose.translate(0,0,0); pose.scale(1/16F,1/16F,1/16F);
        collector.submitModel(stone, state, pose, RenderTypes.entityCutout(STONE), state.lightCoords, 0, 0);
        collector.submitModel(rune, state, pose, RenderTypes.entityTranslucentEmissive(RUNE), 15728880, 0, 0);
        collector.submitModel(crystal, state, pose, RenderTypes.entityTranslucentEmissive(CRYSTAL), 15728880, 0, 0);
        pose.popPose();
    }
    @Override protected boolean affectedByCulling(AncientShrineEntity entity){return false;}
}
