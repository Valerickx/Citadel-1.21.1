package com.github.alexthe666.citadel.client.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ARGB;

import java.awt.image.BufferedImage;

public class VideoFrameTexture extends DynamicTexture {

    public VideoFrameTexture(NativeImage image) {
        super(() -> "video_frame", image);
    }

    public void setPixelsFromBufferedImage(BufferedImage bufferedImage) {
        if (this.getPixels() != null) {
            for(int i = 0; i < Math.min(this.getPixels().getWidth(), bufferedImage.getWidth()); i++){
                for(int j = 0; j < Math.min(this.getPixels().getHeight(), bufferedImage.getHeight()); j++){
                    int color = bufferedImage.getRGB(i, j);
                    int r = color >> 16 & 255;
                    int g = color >> 8 & 255;
                    int b = color & 255;
                    this.getPixels().setPixelABGR(i, j, ARGB.toABGR(ARGB.color(0XFF, r, g, b)));
                }
            }
            this.upload();
        }
    }
}
