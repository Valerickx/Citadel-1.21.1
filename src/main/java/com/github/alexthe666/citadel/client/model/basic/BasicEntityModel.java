package com.github.alexthe666.citadel.client.model.basic;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

import java.util.function.Function;

public abstract class BasicEntityModel<T extends Entity> extends Model<T> {
    public int textureWidth = 64;
    public int textureHeight = 32;

    protected BasicEntityModel() {
        this(location -> null);
    }

    protected BasicEntityModel(Function<Identifier, RenderType> renderType) {
        // Legacy Citadel models maintain their own AdvancedModelBox hierarchy,
        // but 26.2 requires every Model to have a non-null root ModelPart.
        super(new ModelPart(java.util.List.of(), java.util.Map.of()), renderType);
    }

    public abstract Iterable<BasicModelPart> parts();

    public abstract void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch);

    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTick) {
    }
}
