package com.github.alexthe666.citadel.client.gui;

import com.github.alexthe666.citadel.client.rewards.CitadelPatreonRenderer;
import com.github.alexthe666.citadel.server.entity.CitadelEntityData;
import com.github.alexthe666.citadel.server.message.PropertiesMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

public class GuiCitadelPatreonConfig extends OptionsSubScreen {

    private ExtendedSlider distSlider;
    private ExtendedSlider speedSlider;
    private ExtendedSlider heightSlider;
    private Button changeButton;
    private float rotateDist;
    private float rotateSpeed;
    private float rotateHeight;
    private String followType;

    public GuiCitadelPatreonConfig(Screen screen, Options options) {
        super(screen, options, Component.translatable("citadel.gui.patreon_config"));
        CompoundTag tag = CitadelEntityData.getOrCreateCitadelTag(Minecraft.getInstance().player);
        this.rotateDist = tag.contains("CitadelRotateDistance") ? tag.getFloat("CitadelRotateDistance").orElse(2F) : 2F;
        this.rotateSpeed = tag.contains("CitadelRotateSpeed") ? tag.getFloat("CitadelRotateSpeed").orElse(1F) : 1F;
        this.rotateHeight = tag.contains("CitadelRotateHeight") ? tag.getFloat("CitadelRotateHeight").orElse(1F) : 1F;
        this.followType = tag.contains("CitadelFollowerType") ? tag.getString("CitadelFollowerType").orElse("citadel") : "citadel";
    }

    public void setSliderValue(int type, float value) {
        CompoundTag tag = CitadelEntityData.getOrCreateCitadelTag(Minecraft.getInstance().player);
        if (type == 0) {
            rotateDist = value;
            tag.putFloat("CitadelRotateDistance", value);
            distSlider.setValue(value);
        }
        if (type == 1) {
            rotateSpeed = value;
            tag.putFloat("CitadelRotateSpeed", value);
            speedSlider.setValue(value);
        }
        if (type == 2) {
            rotateHeight = value;
            tag.putFloat("CitadelRotateHeight", value);
        }
        CitadelEntityData.setCitadelTag(Minecraft.getInstance().player, tag);
        ClientPacketDistributor.sendToServer(new PropertiesMessage("CitadelPatreonConfig", tag, Minecraft.getInstance().player.getId()));
    }

    public static float roundTo(float value, int places) {
        return value;
    }

    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.centeredText(this.font, this.title, this.width / 2, 20, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    protected void init() {
        super.init();
        int i = this.width / 2;
        int j = this.height / 6;
        Button doneButton = Button.builder(CommonComponents.GUI_DONE, (p_213079_1_) -> Minecraft.getInstance().setScreen(this.lastScreen)).size(200, 20).pos(i - 100, j + 120).build();
        this.addRenderableWidget(doneButton);
        this.addRenderableWidget(distSlider = new ExtendedSlider(i - 150 / 2 - 25, j + 30, 150, 20, Component.translatable("citadel.gui.orbit_dist").append(Component.translatable(": ")), Component.translatable(""), 0.125F, 5F, rotateDist, 0.1D, 1, true) {
            @Override
            protected void applyValue() {
                GuiCitadelPatreonConfig.this.setSliderValue(0, (float) getValue());
            }
        });

        Button reset1Button = Button.builder(Component.translatable("citadel.gui.reset"), (p_213079_1_) -> this.setSliderValue(0, 0.4F)).size(40, 20).pos(i - 150 / 2 + 135, j + 30).build();
        this.addRenderableWidget(reset1Button);

        this.addRenderableWidget(speedSlider = new ExtendedSlider(i - 150 / 2 - 25, j + 60, 150, 20, Component.translatable("citadel.gui.orbit_speed").append(Component.translatable(": ")), Component.translatable(""), 0.0F, 5F, rotateSpeed, 0.1D, 2, true) {
            @Override
            protected void applyValue() {
                GuiCitadelPatreonConfig.this.setSliderValue(1, (float) getValue());
            }
        });

        Button reset2Button = Button.builder(Component.translatable("citadel.gui.reset"), (p_213079_1_) -> this.setSliderValue(1, 1F / 5F)).size(40, 20).pos(i - 150 / 2 + 135, j + 60).build();
        this.addRenderableWidget(reset2Button);

        this.addRenderableWidget(heightSlider = new ExtendedSlider(i - 150 / 2 - 25, j + 90, 150, 20, Component.translatable("citadel.gui.orbit_height").append(Component.translatable(": ")), Component.translatable(""), 0.0F, 2F, rotateHeight, 0.1D, 2, true) {
            @Override
            protected void applyValue() {
                GuiCitadelPatreonConfig.this.setSliderValue(2, (float) getValue());
            }
        });

        Button reset3Button = Button.builder(Component.translatable("citadel.gui.reset"), (p_213079_1_) -> this.setSliderValue(2, 0.5F)).size(40, 20).pos(i - 150 / 2 + 135, j + 90).build();
        this.addRenderableWidget(reset3Button);

        changeButton = Button.builder(getTypeText(), (p_213079_1_) -> {
            this.followType = CitadelPatreonRenderer.getIdOfNext(followType);
            CompoundTag tag = CitadelEntityData.getOrCreateCitadelTag(Minecraft.getInstance().player);
            tag.putString("CitadelFollowerType", followType);
            CitadelEntityData.setCitadelTag(Minecraft.getInstance().player, tag);
            ClientPacketDistributor.sendToServer(new PropertiesMessage("CitadelPatreonConfig", tag, Minecraft.getInstance().player.getId()));
            changeButton.setMessage(getTypeText());
        }).size(200, 20).pos(i - 100, j).build();
        this.addRenderableWidget(changeButton);
    }

    @Override
    protected void addOptions() {

    }

    private Component getgetTypeText() {
        return Component.translatable("citadel.gui.follower_type").append(Component.translatable("citadel.follower." + followType));
    }

    private Component getTypeText() {
        return Component.translatable("citadel.gui.follower_type").append(Component.translatable("citadel.follower." + followType));
    }
}
