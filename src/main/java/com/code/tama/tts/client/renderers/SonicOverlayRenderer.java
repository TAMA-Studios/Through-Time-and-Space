/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers;

import com.code.tama.triggerapi.RayTraceUtils;
import com.code.tama.tts.server.items.SonicItem;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class SonicOverlayRenderer {
    public static int light = 0xf00f0;
    public static int white = 0xFFFFFF;

    public static void Render(PoseStack stack) {
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

            stack.translate(30, 2.5, 0);

            builder.vertex(stack.last().pose(), 0, 25, 0)
                    .uv((float) 1 / 256, (float) 45 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 25, 25, 0)
                    .uv((float) 23 / 256, (float) 45 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 25, 0, 0)
                    .uv((float) 23 / 256, (float) 23 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 0, 0, 0)
                    .uv((float) 1 / 256, (float) 23 / 256)
                    .endVertex();

            stack.translate(25, 0, 0);

            builder.vertex(stack.last().pose(), 0, 25, 0)
                    .uv((float) 1 / 256, (float) 45 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 25, 25, 0)
                    .uv((float) 23 / 256, (float) 45 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 25, 0, 0)
                    .uv((float) 23 / 256, (float) 23 / 256)
                    .endVertex();
            builder.vertex(stack.last().pose(), 0, 0, 0)
                    .uv((float) 1 / 256, (float) 23 / 256)
                    .endVertex();

            stack.popPose();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            BufferUploader.drawWithShader(builder.end());

            stack.pushPose();
            stack.translate(5, Minecraft.getInstance().getWindow().getGuiScaledHeight() - 35, 0);
            // Main square
            //            sonicItem.InteractionType.getIcon().getDefaultInstance()
            Minecraft.getInstance()
                    .getItemRenderer()
                    .renderStatic(
                            Blocks.GRANITE.asItem().getDefaultInstance(),
                            ItemDisplayContext.NONE,
                            0xf000f0,
                            0,
                            stack,
                            Minecraft.getInstance().renderBuffers().bufferSource(),
                            Minecraft.getInstance().level,
                            0);

            stack.popPose();

            stack.pushPose();

            stack.translate(5, Minecraft.getInstance().getWindow().getGuiScaledHeight() - 35, 0);

            HitResult result = RayTraceUtils.rayTraceFromEntity(Minecraft.getInstance().player, 10, false);
            if(result.getType().equals(HitResult.Type.BLOCK)) {
                assert Minecraft.getInstance().level != null;
                BlockPos hit = new BlockPos((int) result.getLocation().x, (int) result.getLocation().y, (int) result.getLocation().z);
                BlockState state = Minecraft.getInstance().level.getBlockState(hit);
                if(state != null) {
                    BlockEntity ent = Minecraft.getInstance().level.getBlockEntity(hit);
                    if(ent != null && ent.getCapability(ForgeCapabilities.ENERGY).isPresent())
                        Minecraft.getInstance().font.drawInBatch(Component.literal(String.format("FE: %s", ent.getCapability(ForgeCapabilities.ENERGY).orElseGet(null).getEnergyStored())).withStyle(ChatFormatting.WHITE), -40, 5, white, false, stack.last().pose(), MultiBufferSource.immediate(builder), Font.DisplayMode.NORMAL, 0, light);
                }
            }

            stack.popPose();

            RenderSystem.disableBlend();
        }
    }
}
