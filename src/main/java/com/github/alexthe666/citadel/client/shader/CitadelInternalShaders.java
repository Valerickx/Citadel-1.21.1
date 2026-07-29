package com.github.alexthe666.citadel.client.shader;

import com.mojang.blaze3d.pipeline.RenderPipeline;

import javax.annotation.Nullable;

public class CitadelInternalShaders {
    private static RenderPipeline renderTypeRainbowAura;

    @Nullable
    public static RenderPipeline getRenderTypeRainbowAura() {
        return renderTypeRainbowAura;
    }

    public static void setRenderTypeRainbowAura(RenderPipeline instance) {
        renderTypeRainbowAura = instance;
    }
}
