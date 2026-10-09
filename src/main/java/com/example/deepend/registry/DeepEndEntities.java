package com.example.deepend.registry;

import com.example.deepend.DeepEnd;
import com.example.deepend.entity.AncientShrineEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class DeepEndEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DeepEnd.MOD_ID);
    public static final RegistryObject<EntityType<AncientShrineEntity>> ANCIENT_SHRINE = ENTITIES.register("ancient_shrine",
            () -> EntityType.Builder.of(AncientShrineEntity::new, MobCategory.MISC)
                    .sized(17.0F, 20.0F).noSummon().noLootTable().clientTrackingRange(64).updateInterval(1)
                    .build(ENTITIES.key("ancient_shrine")));
    private DeepEndEntities() {}
}
