package com.github.alexthe666.citadel.client.shader;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public class CitadelShaderRenderTypes {
    public static RenderType getRainbowAura(Identifier locationIn) {
        return RenderType.entityCutoutNoCull(locationIn);
    }
}
