/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.gui;

import com.code.tama.triggerapi.JavaInJSON.JavaJSONRenderer;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.C2S.dimensions.ChameleonCircuitActionC2SPacket;
import com.code.tama.tts.core.networking.packets.C2S.dimensions.ChameleonCircuitActionC2SPacket.Action;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

import static com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability.GetTARDISCapSupplier;

public class ChameleonCircuitScreen extends Screen {

    private static final int PANEL_WIDTH = 220;
    private static final int PANEL_HEIGHT = 170;
    private static final int MODEL_AREA_HEIGHT = 100;
    private static final int FULLBRIGHT = 0xF000F0;

    private float modelYaw = 0f;

    public ChameleonCircuitScreen() {
        super(Component.literal("Chameleon Circuit"));
    }

    @Override
    protected void init() {
        super.init();
        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;

        this.addRenderableWidget(Button.builder(Component.literal("<"), b -> send(Action.PREV_GROUP))
                .bounds(left + 8, top + 20, 20, 60).build());

        this.addRenderableWidget(Button.builder(Component.literal(">"), b -> send(Action.NEXT_GROUP))
                .bounds(left + PANEL_WIDTH - 28, top + 20, 20, 60).build());

        this.addRenderableWidget(Button.builder(Component.literal("-"), b -> send(Action.PREV_VARIANT))
                .bounds(left + PANEL_WIDTH / 2 - 42, top + MODEL_AREA_HEIGHT + 28, 20, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("+"), b -> send(Action.NEXT_VARIANT))
                .bounds(left + PANEL_WIDTH / 2 + 22, top + MODEL_AREA_HEIGHT + 28, 20, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Next Collection"), b -> send(Action.NEXT_COLLECTION))
                .bounds(left + PANEL_WIDTH / 2 - 60, top + PANEL_HEIGHT - 28, 120, 20).build());
    }

    private void send(Action action) {
        Networking.sendToServer(new ChameleonCircuitActionC2SPacket(action));
        Minecraft.getInstance().getSoundManager()
                .play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK.value(), 1.0f));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);

        int left = (this.width - PANEL_WIDTH) / 2;
        int top = (this.height - PANEL_HEIGHT) / 2;

        guiGraphics.fill(left, top, left + PANEL_WIDTH, top + PANEL_HEIGHT, 0xAA1A1A2A);
        guiGraphics.renderOutline(left, top, PANEL_WIDTH, PANEL_HEIGHT, 0xFF55AAFF);

        render3DExterior(guiGraphics, left + PANEL_WIDTH / 2, top + MODEL_AREA_HEIGHT);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        GetTARDISCapSupplier(Minecraft.getInstance().level).ifPresent(cap -> {
            var exterior = cap.GetData().getExteriorModel();
            String line1 = exterior.getName();
            String line2 = exterior.getParent() + "  \u2022  " + exterior.getCollection();

            guiGraphics.drawCenteredString(this.font, line1, left + PANEL_WIDTH / 2, top + MODEL_AREA_HEIGHT + 6, 0xFFFFFF);
            guiGraphics.drawCenteredString(this.font, line2, left + PANEL_WIDTH / 2, top + MODEL_AREA_HEIGHT + 16, 0xAAAAAA);
        });
    }

    private void render3DExterior(GuiGraphics guiGraphics, int centerX, int centerY) {
        GetTARDISCapSupplier(Minecraft.getInstance().level).ifPresent(cap -> {
            var root = cap.GetClientData().getBaseRoot();
            if (root == null) return;

            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(centerX, centerY, 100);
            poseStack.scale(1, 1, 1); // flip: GUI space is Y-down, model space isn't
            poseStack.scale(14f, 14f, 14f);
            poseStack.mulPose(Axis.XN.rotationDegrees(15f));
            poseStack.mulPose(Axis.YP.rotationDegrees(this.modelYaw));

            Lighting.setupForEntityInInventory();
            MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

            var exteriorRenderer = cap.GetClientData().getExteriorRenderer();
            var texture = exteriorRenderer.getRenderType(cap.GetData().getExteriorModel().getTexture());
            var lightmap = exteriorRenderer.getRenderType(cap.GetData().getExteriorModel().getLightMap());

            cap.GetClientData().getLeftDoor().setRotation(0, 0, 0);
            cap.GetClientData().getRightDoor().setRotation(0, 0, 0);
            renderBone(root, poseStack, bufferSource.getBuffer(texture));
            renderBone(cap.GetClientData().getDoors(), poseStack, bufferSource.getBuffer(texture));

            renderBone(root, poseStack, bufferSource.getBuffer(lightmap));
            renderBone(cap.GetClientData().getDoors(), poseStack, bufferSource.getBuffer(lightmap));

            bufferSource.endBatch();
            Lighting.setupFor3DItems();
            poseStack.popPose();
        });
    }

    private static void renderBone(JavaJSONRenderer bone, PoseStack poseStack, VertexConsumer buffer) {
        bone.render(poseStack, buffer, FULLBRIGHT, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0) {
            this.modelYaw += (float) dragX;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}