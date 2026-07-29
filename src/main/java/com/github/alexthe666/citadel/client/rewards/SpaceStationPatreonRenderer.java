package com.github.alexthe666.citadel.client.rewards;

import com.github.alexthe666.citadel.ClientProxy;
import com.github.alexthe666.citadel.client.texture.CitadelTextureManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class SpaceStationPatreonRenderer extends CitadelPatreonRenderer {

    private static final Identifier CITADEL_TEXTURE = Identifier.fromNamespaceAndPath("citadel", "textures/patreon/citadel_model.png");
    private static final Identifier CITADEL_LIGHTS_TEXTURE = Identifier.fromNamespaceAndPath("citadel", "textures/patreon/citadel_model_glow.png");
    private final Identifier id;
    private int[] colors;

    public SpaceStationPatreonRenderer(Identifier id, int[] colors) {
        this.id = id;
        this.colors = colors;
    }


    @Override
    public void render(PoseStack matrixStackIn, OrderedSubmitNodeCollector buffer, int light, float partialTick, LivingEntity entity, float distanceIn, float rotateSpeed, float rotateHeight) {
        float tick = entity.tickCount + partialTick;
        float bob = (float) (Math.sin(tick * 0.1F) * 1 * 0.05F - 1 * 0.05F);
        float scale = 0.4F;
        float rotation = Mth.wrapDegrees((tick * rotateSpeed) % 360);
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(rotation));
        matrixStackIn.translate(0, entity.getBbHeight() + bob + (rotateHeight - 1F), entity.getBbWidth() * distanceIn);
        matrixStackIn.pushPose();
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(75));
        matrixStackIn.scale(scale, scale, scale);
        matrixStackIn.mulPose(Axis.XP.rotationDegrees(90));
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(rotation * 10));
        ClientProxy.CITADEL_MODEL.resetToDefaultPose();
        buffer.submitModel(ClientProxy.CITADEL_MODEL, RenderTypes.entityCutout(CitadelTextureManager.getColorMappedTexture(id, CITADEL_TEXTURE, colors)), matrixStackIn, light, 0, -1, null);
        buffer.submitModel(ClientProxy.CITADEL_MODEL, RenderTypes.eyes(CITADEL_LIGHTS_TEXTURE), matrixStackIn, light, 0, -1, null);
        matrixStackIn.popPose();
        matrixStackIn.popPose();
    }
}
