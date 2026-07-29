package com.github.alexthe666.citadel.client.shader;

import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostEffectRegistry {

    private static List<Identifier> registry = new ArrayList<>();

    private static Map<Identifier, PostEffect> postEffects = new HashMap<>();

    public static void clear(){
        for(PostEffect postEffect : postEffects.values()){
            postEffect.close();
        }
        postEffects.clear();
    }

    public static void registerEffect(Identifier Identifier) {
        registry.add(Identifier);
    }

    public static void onInitializeOutline() {
        clear();
    }

    public static void resize(int x, int y) {
        for (PostEffect postEffect : postEffects.values()) {
            postEffect.resize(x, y);
        }
    }

    public static RenderTarget getRenderTargetFor(Identifier Identifier) {
        PostEffect effect = postEffects.get(Identifier);
        return effect == null ? null : effect.getRenderTarget();
    }

    public static void renderEffectForNextTick(Identifier Identifier) {
        PostEffect effect = postEffects.get(Identifier);
        if (effect != null) {
            effect.setEnabled(true);
        }
    }

    public static void blitEffects() {
    }

    private static class PostEffect implements AutoCloseable {
        private RenderTarget renderTarget;
        private boolean enabled;

        public PostEffect(RenderTarget renderTarget, boolean enabled) {
            this.renderTarget = renderTarget;
            this.enabled = enabled;
        }

        public void resize(int x, int y) {
        }

        public RenderTarget getRenderTarget() {
            return renderTarget;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public void close() {
        }
    }
}