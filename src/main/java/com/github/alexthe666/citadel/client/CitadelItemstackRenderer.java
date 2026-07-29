package com.github.alexthe666.citadel.client;

/**
 * Kept as a source-compatible marker for integrations which used Citadel's
 * pre-26.2 custom item renderer.  Minecraft 26.2 renders item models through
 * ItemModelResolver/ItemStackRenderState, so the old immediate-mode renderer
 * no longer has a valid extension point.
 */
public final class CitadelItemstackRenderer {
    private CitadelItemstackRenderer() {
    }
/*
        float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();
        float ticksExisted = Util.getMillis() / 50F + partialTicks;
        int id = Minecraft.getInstance().player == null ? 0 : Minecraft.getInstance().player.getId();
        
        if (stack.getItem() == Citadel.FANCY_ITEM.get()) {
            Random random = new Random();
            boolean animateAnyways = false;
            ItemStack toRender = null;
            
            FancyItemDisplay display = stack.get(CitadelDataComponents.FANCY_ITEM_DISPLAY.get());
            if (display != null && !display.displayItem().isEmpty()) {
                var item = BuiltInRegistries.ITEM.get(Identifier.parse(display.displayItem()));
                toRender = new ItemStack(item);
                // Note: displayItemNbt is not supported in 1.21 component system for nested items
            }
            
            if (toRender == null || toRender.isEmpty()) {
                animateAnyways = true;
                toRender = new ItemStack(Items.BARRIER);
            }
            
            poseStack.pushPose();
            poseStack.translate(0.5F, 0.5f, 0.5f);
            
            if (display != null && display.displayShake()) {
                poseStack.translate((random.nextFloat() - 0.5F) * 0.1F, (random.nextFloat() - 0.5F) * 0.1F, (random.nextFloat() - 0.5F) * 0.1F);
            }
            if (animateAnyways || (display != null && display.displayBob())) {
                poseStack.translate(0, 0.05F + 0.1F * Mth.sin(0.3F * ticksExisted), 0);
            }
            if (display != null && display.displaySpin()) {
                poseStack.mulPose(Axis.YP.rotationDegrees(6 * ticksExisted));
            }
            if (animateAnyways || (display != null && display.displayZoom())) {
                float scale = (float) (1F + 0.15F * (Math.sin(ticksExisted * 0.3F) + 1F));
                poseStack.scale(scale, scale, scale);
            }
            if (display != null && display.displayScale() != 1.0F) {
                float scale = display.displayScale();
                poseStack.scale(scale, scale, scale);
            }
            
            Minecraft.getInstance().getItemRenderer().renderStatic(toRender, displayContext, packedLight, packedOverlay, poseStack, buffer, null, id);
            poseStack.popPose();
        }
        
        if (stack.getItem() == Citadel.EFFECT_ITEM.get()) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            Holder<MobEffect> effect;
            if (mobEffectList == null) {
                mobEffectList = BuiltInRegistries.MOB_EFFECT.holders().toList();
            }
            int size = mobEffectList.size();
            int time = (int) (Util.getMillis() / 500);
            effect = mobEffectList.get(time % size);
            if (effect == null) {
                effect = MobEffects.MOVEMENT_SPEED.getDelegate();
            }
            MobEffectTextureManager potionspriteuploader = Minecraft.getInstance().getMobEffectTextures();
            poseStack.pushPose();
            poseStack.translate(0, 0, 0.5F);
            TextureAtlasSprite sprite = potionspriteuploader.get(effect);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, sprite.atlasLocation());
            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
            Matrix4f mx = poseStack.last().pose();
            int br = 255;
            bufferbuilder.addVertex(mx, (float) 1, (float) 1, (float) 0).setUv(sprite.getU1(), sprite.getV0()).setColor(br, br, br, 255).setLight(packedLight);
            bufferbuilder.addVertex(mx, (float) 0, (float) 1, (float) 0).setUv(sprite.getU0(), sprite.getV0()).setColor(br, br, br, 255).setLight(packedLight);
            bufferbuilder.addVertex(mx, (float) 0, (float) 0, (float) 0).setUv(sprite.getU0(), sprite.getV1()).setColor(br, br, br, 255).setLight(packedLight);
            bufferbuilder.addVertex(mx, (float) 1, (float) 0, (float) 0).setUv(sprite.getU1(), sprite.getV1()).setColor(br, br, br, 255).setLight(packedLight);
            poseStack.popPose();
        }
        
        if (stack.getItem() == Citadel.ICON_ITEM.get()) {
            Identifier texture = DEFAULT_ICON_TEXTURE;
            IconItemDisplay display = stack.get(CitadelDataComponents.ICON_ITEM_DISPLAY.get());
            if (display != null && !display.iconLocation().isEmpty()) {
                String iconLocationStr = display.iconLocation();
                if (LOADED_ICONS.containsKey(iconLocationStr)) {
                    texture = LOADED_ICONS.get(iconLocationStr);
                } else {
                    texture = Identifier.parse(iconLocationStr);
                    LOADED_ICONS.put(iconLocationStr, texture);
                }
            }
            poseStack.pushPose();
            poseStack.translate(0, 0, 0.5F);
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, texture);
            Tesselator tessellator = Tesselator.getInstance();
            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
            Matrix4f mx = poseStack.last().pose();
            int br = 255;
            bufferbuilder.addVertex(mx, (float) 1, (float) 1, (float) 0).setUv(1, 0).setColor(br, br, br, 255).setLight(packedLight);
            bufferbuilder.addVertex(mx, (float) 0, (float) 1, (float) 0).setUv(0, 0).setColor(br, br, br, 255).setLight(packedLight);
            bufferbuilder.addVertex(mx, (float) 0, (float) 0, (float) 0).setUv(0, 1).setColor(br, br, br, 255).setLight(packedLight);
            bufferbuilder.addVertex(mx, (float) 1, (float) 0, (float) 0).setUv(1, 1).setColor(br, br, br, 255).setLight(packedLight);
            poseStack.popPose();
        }
    }
*/
}
