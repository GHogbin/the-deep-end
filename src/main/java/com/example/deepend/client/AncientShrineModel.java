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
        root.addOrReplaceChild(id, CubeListBuilder.create().texOffs(0, 0).addBox(x, -y - h, z, w, h, d), PartPose.ZERO);
    }
    private static void rotated(PartDefinition root, String id, float x, float y, float z, float w, float h, float d, float angle) {
        root.addOrReplaceChild(id, CubeListBuilder.create().texOffs(0, 0).addBox(0, -h, -d / 2, w, h, d), PartPose.offsetAndRotation(x, y, z, 0, 0, angle));
    }
    public static LayerDefinition createStone() {
        MeshDefinition mesh = new MeshDefinition(); PartDefinition r = mesh.getRoot();
        // Three broad levels reproduce the reference's stepped ruin platform.
        cube(r,"plinth",-120,0,-120,240,16,240); cube(r,"deck",-96,-16,-96,192,16,192); cube(r,"inner",-80,-32,-80,160,16,160);
        for (int side : new int[]{-1,1}) {
            float x=side*80, tall=side<0?260:220;
            cube(r,"tower"+side,x-16,-48,48,32,tall,32); cube(r,"buttress"+side,x-side*16,-48,64,32,tall-48,32);
            cube(r,"crown"+side,x-22,-48-tall,45,44,24,38);
            for(int i=0;i<4;i++) cube(r,"spire"+side+i,x-15,-48-(48+i*16),-80+i*8,30,48-i*5,30);
        }
        // Four heavy diamond arms, intentionally separated at the top and bottom.
        rotated(r,"armUL",-64,-150,0,128,14,18,-Mth.PI/4); rotated(r,"armUR",0,-150,0,128,14,18,Mth.PI/4);
        rotated(r,"armLL",-64,-76,0,128,14,18,Mth.PI/4); rotated(r,"armLR",0,-76,0,128,14,18,-Mth.PI/4);
        for(int i=0;i<6;i++){ cube(r,"collarA"+i,-64+i*22,-128-i*18,-12,20,12,24); cube(r,"collarB"+i,10+i*22,-128-i*18,-12,20,12,24); }
        return LayerDefinition.create(mesh, 32, 32);
    }
    public static LayerDefinition createRune() {
        MeshDefinition mesh=new MeshDefinition(); PartDefinition r=mesh.getRoot();
        for(int side:new int[]{-1,1}){float x=side*80; for(int i=0;i<9;i++) cube(r,"towerRune"+side+i,x-2,-55-i*27,30,4,18,2); for(int i=0;i<4;i++) cube(r,"spireRune"+side+i,x-2,-56-i*22,-81+i*8,4,14,2);}
        cube(r,"floorLine",-4,-33,-80,8,3,160); cube(r,"floorLine2",-72,-33,34,144,3,8);
        return LayerDefinition.create(mesh,32,32);
    }
    public static LayerDefinition createCrystal() {
        MeshDefinition mesh=new MeshDefinition(); PartDefinition r=mesh.getRoot();
        cube(r,"core",-18,-118,-18,36,68,36); cube(r,"shardL",-34,-104,-10,14,52,18); cube(r,"shardR",20,-104,-10,14,52,18);
        cube(r,"top",-8,-188,-8,16,22,16); cube(r,"bottom",-8,-48,-8,16,22,16);
        for(int i=0;i<4;i++) cube(r,"orbital"+i,-6,-68-i*30,-6,12,18,12);
        return LayerDefinition.create(mesh,32,32);
    }
}
