package com.github.alexthe666.citadel.client.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;

public class CitadelTextureManager {

    private static final Map<Identifier, Identifier> COLOR_MAPPED_TEXTURES = new HashMap<>();

    public static Identifier getColorMappedTexture(Identifier textureLoc, int[] colors){
        return getColorMappedTexture(textureLoc, textureLoc, colors);
    }

    public static Identifier getColorMappedTexture(Identifier namespace, Identifier textureLoc, int[] colors){
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = textureManager.getTexture(namespace);
        if (abstracttexture == textureManager.getTexture(MissingTextureAtlasSprite.getLocation())) {
            textureManager.register(namespace, new ColorMappedTexture(textureLoc, colors));
        }
        return namespace;
    }

    public static VideoFrameTexture getVideoTexture(Identifier namespace, int defaultWidth, int defaultHeight){
        TextureManager textureManager = Minecraft.getInstance().getTextureManager();
        AbstractTexture abstracttexture = textureManager.getTexture(namespace);
        if (abstracttexture == textureManager.getTexture(MissingTextureAtlasSprite.getLocation())) {
            abstracttexture = new VideoFrameTexture(new NativeImage(defaultWidth, defaultHeight, false));
            textureManager.register(namespace, abstracttexture);
        }
        return abstracttexture instanceof VideoFrameTexture ? (VideoFrameTexture) abstracttexture : null;
    }
}
