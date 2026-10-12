package com.example.deepend.client;

import com.example.deepend.entity.AncientShrineEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

/** Baked custom geometry: masonry, frame, rune channels, and crystal shards are separate parts. */
public final class AncientShrineModel extends EntityModel<EntityRenderState> {
    public static final ModelLayerLocation STONE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("deep_end", "ancient_shrine"), "stone");
    public static final ModelLayerLocation RUNE_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("deep_end", "ancient_shrine"), "rune");
    public static final ModelLayerLocation CRYSTAL_LAYER = new ModelLayerLocation(Identifier.fromNamespaceAndPath("deep_end", "ancient_shrine"), "crystal");

    public AncientShrineModel(ModelPart root) { super(root); }

    private static void cube(PartDefinition root, String id, float x, float y, float z, float w, float h, float d) {
        // The authored shrine uses a ground-up coordinate system: negative y values are
        // heights above the entity origin, while the baked cube starts at -y.
        root.addOrReplaceChild(id, CubeListBuilder.create().texOffs(0, 0).addBox(x, -y, z, w, h, d), PartPose.ZERO);
    }
    private static void rotated(PartDefinition root, String id, float x, float y, float z, float w, float h, float d, float angle) {
        root.addOrReplaceChild(id, CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, -d / 2, w, h, d), PartPose.offsetAndRotation(x, -y, z, 0, 0, angle));
    }
    public static LayerDefinition createStone() {
        MeshDefinition mesh = new MeshDefinition(); PartDefinition r = mesh.getRoot();
        // Three stepped tiers and a front stair reproduce the reference silhouette.
        cube(r,"plinth",-120,0,-120,240,16,240);
        cube(r,"plinthInset",-108,-16,-108,216,12,216);
        cube(r,"deck",-96,-28,-96,192,16,192);
        cube(r,"inner",-80,-44,-80,160,16,160);
        for (int i=0;i<4;i++) cube(r,"frontStep"+i,-48-i*6,-60-i*12,-128+i*10,96+i*12,12,24);
        cube(r,"frontLanding",-48,-60,-96,96,12,28);
        // Two asymmetrical rear towers and four shorter corner pylons.
        for (int side : new int[]{-1,1}) {
            float x=side*80, tall=side<0?272:240;
            cube(r,"rearTower"+side,x-20,-60,48,40,tall,40);
            cube(r,"rearShoulder"+side,x-30,-60,28,60,64,48);
            cube(r,"rearCrown"+side,x-26,-60-tall,42,52,24,46);
            cube(r,"rearCap"+side,x-14,-84-tall,46,28,28,38);
            for (int tier=0;tier<3;tier++) cube(r,"rearBreak"+side+tier,x-25,-112-tier*42,28,50,12,52);
        }
        for (int side : new int[]{-1,1}) for (int front : new int[]{-1,1}) {
            float x=side*82-14, z=front*82-14;
            cube(r,"cornerBase"+side+front,x,-60,z,28,44,28);
            cube(r,"cornerPylon"+side+front,x+3,-104,z+3,22,92,22);
            cube(r,"cornerCrown"+side+front,x-2,-196,z-2,32,20,32);
        }
        // Broken diamond frame around the floating crystal.
        rotated(r,"armUpperLeft",-78,-184,0,132,18,22,-Mth.PI/4);
        rotated(r,"armUpperRight",0,-184,0,132,18,22,Mth.PI/4);
        rotated(r,"armLowerLeft",-78,-84,0,132,18,22,Mth.PI/4);
        rotated(r,"armLowerRight",0,-84,0,132,18,22,-Mth.PI/4);
        for(int i=0;i<5;i++) {
            cube(r,"upperJointL"+i,-72+i*24,-168-i*20,-14,24,14,28);
            cube(r,"upperJointR"+i,0+i*24,-168-i*20,-14,24,14,28);
        }
        cube(r,"frontPedestal",-20,-60,-20,40,36,40);
        cube(r,"frontPedestalTop",-28,-96,-28,56,12,56);
        return LayerDefinition.create(mesh, 32, 32);
    }
    public static LayerDefinition createRune() {
        MeshDefinition mesh=new MeshDefinition(); PartDefinition r=mesh.getRoot();
        for(int side:new int[]{-1,1}) {
            float x=side*80;
            for(int i=0;i<10;i++) cube(r,"towerRune"+side+i,x-3,-72-i*25,29,6,16,3);
            for(int i=0;i<4;i++) cube(r,"frontRune"+side+i,side*82-3,-118,-88+i*4,6,24,3);
        }
        cube(r,"floorLine",-4,-48,-80,8,3,160);
        cube(r,"floorLine2",-72,-48,34,144,3,8);
        cube(r,"floorLine3",-72,-48,-42,144,3,8);
        cube(r,"pedestalRune",-3,-100,-29,6,20,3);
        return LayerDefinition.create(mesh,32,32);
    }
    public static LayerDefinition createCrystal() {
        MeshDefinition mesh=new MeshDefinition(); PartDefinition r=mesh.getRoot();
        cube(r,"core",-18,-132,-18,36,76,36);
        cube(r,"shardL",-38,-118,-12,16,60,20);
        cube(r,"shardR",22,-118,-12,16,60,20);
        cube(r,"shardTop",-10,-202,-10,20,30,20);
        cube(r,"shardBottom",-10,-56,-10,20,30,20);
        for(int i=0;i<5;i++) cube(r,"orbital"+i,-7,-76-i*29,-7,14,18,14);
        cube(r,"pedestalCrystal",-10,-112,-10,20,24,20);
        return LayerDefinition.create(mesh,32,32);
    }
}
