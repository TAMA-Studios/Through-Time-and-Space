package com.code.tama.tts.client.renderers;

import com.code.tama.tts.server.registries.TTSItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SonicOverlayRenderer {
    public static void Render(PoseStack stack) {
        Player player = Minecraft.getInstance().player;
        if(player.isHolding(TTSItems.SONIC_SCREWDRIVER.get())) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            BufferBuilder builder = Tesselator.getInstance().getBuilder();
            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/widgets.png"));

            stack.pushPose();
            stack.translate(5, Minecraft.getInstance().getWindow().getGuiScaledHeight() - 35, 0);

            builder.vertex(stack.last().pose(), 0, 30, 0).uv((float) 1 / 256, (float) 45 / 256).endVertex();
            builder.vertex(stack.last().pose(),30, 30, 0).uv((float) 23 / 256, (float) 45 / 256).endVertex();
            builder.vertex(stack.last().pose(), 30, 0, 0).uv((float) 23 / 256, (float) 23 / 256).endVertex();
            builder.vertex(stack.last().pose(), 0, 0, 0).uv((float) 1 / 256, (float) 23 / 256).endVertex();

            stack.translate(30, 2.5, 0);

            builder.vertex(stack.last().pose(), 0, 25, 0).uv((float) 1 / 256, (float) 45 / 256).endVertex();
            builder.vertex(stack.last().pose(),25, 25, 0).uv((float) 23 / 256, (float) 45 / 256).endVertex();
            builder.vertex(stack.last().pose(), 25, 0, 0).uv((float) 23 / 256, (float) 23 / 256).endVertex();
            builder.vertex(stack.last().pose(), 0, 0, 0).uv((float) 1 / 256, (float) 23 / 256).endVertex();

            stack.translate(25, 0, 0);

            builder.vertex(stack.last().pose(), 0, 25, 0).uv((float) 1 / 256, (float) 45 / 256).endVertex();
            builder.vertex(stack.last().pose(),25, 25, 0).uv((float) 23 / 256, (float) 45 / 256).endVertex();
            builder.vertex(stack.last().pose(), 25, 0, 0).uv((float) 23 / 256, (float) 23 / 256).endVertex();
            builder.vertex(stack.last().pose(), 0, 0, 0).uv((float) 1 / 256, (float) 23 / 256).endVertex();

            stack.popPose();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            BufferUploader.drawWithShader(builder.end());
            RenderSystem.disableBlend();
        }
    }
}