package com.example.deepend.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;

/** Server-persisted anchor for the client-rendered shrine model. */
public final class AncientShrineEntity extends Entity {
    public AncientShrineEntity(EntityType<? extends AncientShrineEntity> type, Level level) { super(type, level); }
    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {}
    @Override public boolean hurtServer(ServerLevel level, DamageSource source, float amount) { return false; }
    @Override protected void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {}
    @Override protected void addAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output) {}
    @Override public boolean shouldRenderAtSqrDistance(double distance) { return true; }
}
