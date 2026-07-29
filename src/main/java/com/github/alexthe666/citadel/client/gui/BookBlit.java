package com.github.alexthe666.citadel.client.gui;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public class BookBlit {

    public static void blitWithColor(GuiGraphicsExtractor guiGraphics, Identifier p_283377_, int p_281970_, int p_282111_, int p_283134_, int p_282778_, int p_281478_, int p_281821_, int r, int g, int b, int a) {
        guiGraphics.blit(p_283377_, p_281970_, p_282111_, p_281478_, p_281821_, p_283134_, p_282778_, p_281478_, p_281821_);
    }

    public static void blitWithColor(GuiGraphicsExtractor guiGraphics, Identifier p_283573_, int p_283574_, int p_283670_, int p_283545_, float p_283029_, float p_283061_, int p_282845_, int p_282558_, int p_282832_, int p_281851_, int r, int g, int b, int a) {
        guiGraphics.blit(p_283573_, p_283574_, p_283670_, p_282845_, p_282558_, p_283029_, p_283061_, p_282845_, p_282558_);
    }

    public static void blitWithColor(GuiGraphicsExtractor guiGraphics, Identifier p_282034_, int p_283671_, int p_282377_, int p_282058_, int p_281939_, float p_282285_, float p_283199_, int p_282186_, int p_282322_, int p_282481_, int p_281887_, int r, int g, int b, int a) {
        guiGraphics.blit(p_282034_, p_283671_, p_282377_, p_282058_, p_281939_, p_282285_, p_283199_, p_282186_, p_282322_);
    }

    public static void blitWithColor(GuiGraphicsExtractor guiGraphics, Identifier p_283272_, int p_283605_, int p_281879_, float p_282809_, float p_282942_, int p_281922_, int p_282385_, int p_282596_, int p_281699_, int r, int g, int b, int a) {
        guiGraphics.blit(p_283272_, p_283605_, p_281879_, p_281922_, p_282385_, p_282809_, p_282942_, p_281922_, p_282385_);
    }
}
