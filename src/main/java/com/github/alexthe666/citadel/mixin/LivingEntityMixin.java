package com.github.alexthe666.citadel.mixin;

import com.github.alexthe666.citadel.CitadelConstants;
import com.github.alexthe666.citadel.server.entity.ICitadelDataEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ICitadelDataEntity {

    private CompoundTag citadelData = new CompoundTag();

    protected LivingEntityMixin(EntityType<? extends Entity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), remap = CitadelConstants.REMAPREFS, method = "defineSynchedData")
    private void citadel_registerData(SynchedEntityData.Builder builder, CallbackInfo ci) {
    }

    @Inject(at = @At("TAIL"), remap = CitadelConstants.REMAPREFS, method = "addAdditionalSaveData")
    private void citadel_writeAdditional(ValueOutput output, CallbackInfo ci) {
        CompoundTag citadelDat = getCitadelEntityData();
        if (citadelDat != null) {
            output.store("CitadelData", CompoundTag.CODEC, citadelDat);
        }
    }

    @Inject(at = @At("TAIL"), remap = CitadelConstants.REMAPREFS, method = "readAdditionalSaveData")
    private void citadel_readAdditional(ValueInput input, CallbackInfo ci) {
        input.read("CitadelData", CompoundTag.CODEC).ifPresent(this::setCitadelEntityData);
    }

    public CompoundTag getCitadelEntityData() {
        return citadelData;
    }

    public void setCitadelEntityData(CompoundTag nbt) {
        citadelData = nbt;
    }
}
