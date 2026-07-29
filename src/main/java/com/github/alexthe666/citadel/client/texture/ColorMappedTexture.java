package com.github.alexthe666.citadel.client.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureContents;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.ARGB;

import javax.annotation.Nullable;
import java.io.InputStream;

public class ColorMappedTexture extends SimpleTexture {

    private int[] colors;

    public ColorMappedTexture(Identifier identifier, int[] colors) {
        super(identifier);
        this.colors = colors;
    }

    @Override
    public TextureContents loadContents(ResourceManager resourceManager) {
        Identifier location = resourceId();
        NativeImage nativeimage = getNativeImage(resourceManager, location);
        if (nativeimage != null) {
            return new TextureContents(nativeimage, null);
        }
        return new TextureContents(new NativeImage(1, 1, false), null);
    }

    private NativeImage getNativeImage(ResourceManager resourceManager, @Nullable Identifier identifier) {
        if (identifier == null) {
            return null;
        }
        try {
            Resource resource = resourceManager.getResourceOrThrow(identifier);
            InputStream inputstream = resource.open();
            NativeImage nativeimage = NativeImage.read(inputstream);
            inputstream.close();
            return nativeimage;
        } catch (Throwable throwable1) {
            return null;
        }
    }

    private void processColorMap(NativeImage nativeImage, NativeImage colorMap) {
        int[] fromColorMap = new int[colorMap.getHeight()];
        for (int i = 0; i < fromColorMap.length; i++) {
            fromColorMap[i] = colorMap.getPixel(0, i);
        }
        for (int i = 0; i < nativeImage.getWidth(); i++) {
            for (int j = 0; j < nativeImage.getHeight(); j++) {
                int colorAt = nativeImage.getPixel(i, j);
                if (ARGB.alpha(ARGB.fromABGR(colorAt)) == 0) {
                    continue;
                }
                int replaceIndex = -1;
                for (int k = 0; k < fromColorMap.length; k++) {
                    if (colorAt == fromColorMap[k]) {
                        replaceIndex = k;
                    }
                }
                if (replaceIndex >= 0 && colors.length > replaceIndex) {
                    int r = colors[replaceIndex] >> 16 & 255;
                    int g = colors[replaceIndex] >> 8 & 255;
                    int b = colors[replaceIndex] & 255;
                    nativeImage.setPixelABGR(i, j, ARGB.toABGR(ARGB.color(ARGB.alpha(ARGB.fromABGR(colorAt)), r, g, b)));
                }
            }
        }
    }
}
