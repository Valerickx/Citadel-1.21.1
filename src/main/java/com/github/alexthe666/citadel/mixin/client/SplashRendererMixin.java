package com.github.alexthe666.citadel.mixin.client;

import net.minecraft.client.gui.components.SplashRenderer;
import org.spongepowered.asm.mixin.Mixin;

/** Splash rendering hooks are disabled until the 26.2 extraction API is wired. */
@Mixin(SplashRenderer.class)
public class SplashRendererMixin {
}
