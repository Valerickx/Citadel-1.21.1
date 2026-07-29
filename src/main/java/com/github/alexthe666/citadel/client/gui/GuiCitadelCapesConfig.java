package com.github.alexthe666.citadel.client.gui;

import com.github.alexthe666.citadel.ClientProxy;
import com.github.alexthe666.citadel.client.rewards.CitadelCapes;
import com.github.alexthe666.citadel.server.entity.CitadelEntityData;
import com.github.alexthe666.citadel.server.message.PropertiesMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class GuiCitadelCapesConfig extends OptionsSubScreen {

    @Nullable
    private String capeType;
    private Button button;


    public GuiCitadelCapesConfig(Screen parentScreenIn, Options gameSettingsIn) {
        super(parentScreenIn, gameSettingsIn, Component.translatable("citadel.gui.capes"));
        CompoundTag tag = CitadelEntityData.getOrCreateCitadelTag(Minecraft.getInstance().player);
        capeType = tag.contains("CitadelCapeType") && !tag.getString("CitadelCapeType").orElse("").isEmpty() ? tag.getString("CitadelCapeType").orElse("") : null;
    }

    @Override
    protected void addOptions() {
    }

    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.centeredText(this.font, this.title, this.width / 2, 20, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int i = this.width / 2;
        int j = this.height / 6;
        ClientProxy.hideFollower = true;
        renderBackwardsEntity(guiGraphics, i, j + 144, 60, Minecraft.getInstance().player);
        ClientProxy.hideFollower = false;
    }

    public static void renderBackwardsEntity(GuiGraphicsExtractor guiGraphics, int x, int y, int size, LivingEntity entity) {
        if (entity != null) {
            InventoryScreen.extractEntityInInventoryFollowsMouse(guiGraphics, x - size, y - size * 2, x + size, y, size, 0, 0, 0, entity);
        }
    }


    protected void init() {
        super.init();
        int i = this.width / 2;
        int j = this.height / 6;
        Button doneButton = Button.builder(CommonComponents.GUI_DONE, (p_213079_1_) -> Minecraft.getInstance().setScreen(this.lastScreen)).size(200, 20).pos(i - 100, j + 140).build();
        this.addRenderableWidget(doneButton);
        button = Button.builder(getCapeButtonText(), (p_213079_1_) -> {
            CitadelCapes.Cape currentCape = CitadelCapes.getCapesFor(Minecraft.getInstance().player.getUUID()).stream().filter(cape -> cape.getIdentifier().equals(capeType)).findFirst().orElse(null);
            int index = CitadelCapes.getCapesFor(Minecraft.getInstance().player.getUUID()).indexOf(currentCape);
            int nextIndex = index + 1;
            if (nextIndex >= CitadelCapes.getCapesFor(Minecraft.getInstance().player.getUUID()).size()) {
                capeType = null;
            } else {
                capeType = CitadelCapes.getCapesFor(Minecraft.getInstance().player.getUUID()).get(nextIndex).getIdentifier();
            }
            button.setMessage(getCapeButtonText());
            CompoundTag tag = CitadelEntityData.getOrCreateCitadelTag(Minecraft.getInstance().player);
            tag.putString("CitadelCapeType", capeType == null ? "" : capeType);
            CitadelEntityData.setCitadelTag(Minecraft.getInstance().player, tag);
            ClientPacketDistributor.sendToServer(new PropertiesMessage("CitadelPatreonConfig", tag, Minecraft.getInstance().player.getId()));
        }).size(200, 20).pos(i - 100, j + 20).build();
        this.addRenderableWidget(button);

    }

    private Component getCapeButtonText() {
        return Component.translatable("citadel.gui.cape_type").append(Component.literal(": ")).append(Component.translatable(capeType == null ? "citadel.gui.cape_type.none" : "citadel.cape." + capeType));
    }
}
