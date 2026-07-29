package com.github.alexthe666.citadel.mixin.client;

import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;

/** LevelRenderer extraction hooks are not applicable to the 26.2 renderer. */
@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
}
