/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import com.code.tama.triggerapi.helpers.world.RayTraceUtils;
import com.code.tama.tts.server.items.gadgets.SonicItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class SonicOverlayRenderer {
    public static int light = 0xf00f0;
    public static int white = 0xFFFFFF;

    public static void Render(PoseStack stack, MultiBufferSource.BufferSource bufferSource) {
        Player player = Minecraft.getInstance().player;
        if (player.getMainHandItem().getItem() instanceof SonicItem sonicItem) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            BufferBuilder builder = Tesselator.getInstance().getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/widgets.png"));

            stack.pushPose();
            stack.translate(5, Minecraft.getInstance().getWindow().getGuiScaledHeight() - 35, 0);

            // Main square
            builder.vertex(stack.last().pose(), 0, 30, 0)
                    .uv((float) 1 / 256, (float) 45 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 30, 30, 0)
                    .uv((float) 23 / 256, (float) 45 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 30, 0, 0)
                    .uv((float) 23 / 256, (float) 23 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 0, 0, 0)
                    .uv((float) 1 / 256, (float) 23 / 256)
                    .endVertex();
            //
            //            stack.translate(30, 2.5, 0);
            //
            //            builder.vertex(stack.last().pose(), 0, 25, 0)
            //                    .uv((float) 1 / 256, (float) 45 / 256)
            //                    .endVertex();
            //            builder.vertex(stack.last().pose(), 25, 25, 0)
            //                    .uv((float) 23 / 256, (float) 45 / 256)
            //                    .endVertex();
            //            builder.vertex(stack.last().pose(), 25, 0, 0)
            //                    .uv((float) 23 / 256, (float) 23 / 256)
            //                    .endVertex();
            //            builder.vertex(stack.last().pose(), 0, 0, 0)
            //                    .uv((float) 1 / 256, (float) 23 / 256)
            //                    .endVertex();
            //
            //            stack.translate(25, 0, 0);
            //
            //            builder.vertex(stack.last().pose(), 0, 25, 0)
            //                    .uv((float) 1 / 256, (float) 45 / 256)
            //                    .endVertex();
            //            builder.vertex(stack.last().pose(), 25, 25, 0)
            //                    .uv((float) 23 / 256, (float) 45 / 256)
            //                    .endVertex();
            //            builder.vertex(stack.last().pose(), 25, 0, 0)
            //                    .uv((float) 23 / 256, (float) 23 / 256)
            //                    .endVertex();
            //            builder.vertex(stack.last().pose(), 0, 0, 0)
            //                    .uv((float) 1 / 256, (float) 23 / 256)
            //                    .endVertex();

            stack.popPose();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            BufferUploader.drawWithShader(builder.end());

            stack.pushPose();
            stack.translate(19.5, Minecraft.getInstance().getWindow().getGuiScaledHeight() - 20.5, 0);
            stack.mulPose(Axis.XP.rotationDegrees(180));
            stack.scale(20, 20, 20);
            // Main square

            Minecraft.getInstance()
                    .getItemRenderer()
                    .renderStatic(
                            sonicItem.InteractionType.getIcon().getDefaultInstance(),
                            ItemDisplayContext.GUI,
                            0xf000f0,
                            OverlayTexture.NO_OVERLAY,
                            stack,
                            bufferSource,
                            Minecraft.getInstance().level,
                            0);

            stack.popPose();

            stack.pushPose();

            stack.translate(5, Minecraft.getInstance().getWindow().getGuiScaledHeight() - 35, 0);

            BlockPos hit = RayTraceUtils.getLookingAtBlock(10);
            assert Minecraft.getInstance().level != null;
            if (hit != null)
                if (Minecraft.getInstance().level.getBlockState(hit) != null) {
                    BlockEntity ent = Minecraft.getInstance().level.getBlockEntity(hit);
                    if (ent != null
                            && ent.getCapability(ForgeCapabilities.ENERGY).isPresent())
                        Minecraft.getInstance()
                                .font
                                .drawInBatch(
                                        Component.literal(String.format(
                                                        "FE: %s",
                                                        ent.getCapability(ForgeCapabilities.ENERGY)
                                                                .orElseGet(null)
                                                                .getEnergyStored()))
                                                .withStyle(ChatFormatting.WHITE),
                                        0,
                                        -15,
                                        white,
                                        false,
                                        stack.last().pose(),
                                        bufferSource,
                                        Font.DisplayMode.NORMAL,
                                        0,
                                        light);
                }

            stack.popPose();

            RenderSystem.disableBlend();
        }
    }
}
