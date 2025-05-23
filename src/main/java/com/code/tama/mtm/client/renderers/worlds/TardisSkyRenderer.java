package com.code.tama.mtm.client.renderers.worlds;

import com.code.tama.mtm.client.CustomLevelRenderer;
import com.code.tama.mtm.server.worlds.dimension.MDimensions;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector4i;

import java.util.Random;

import static com.code.tama.mtm.client.CustomLevelRenderer.renderPlanet;
import static net.minecraft.client.renderer.blockentity.TheEndPortalRenderer.END_SKY_LOCATION;

public class TardisSkyRenderer extends AbstractLevelRenderer {

    @Override
    ResourceLocation EffectsLocation() {
        return MDimensions.TARDIS_DIM_TYPE.location();
    }

    @Override
    boolean ShouldRenderVoid() {
        return true;
    }

    @Override
    void RenderLevel(@NotNull Camera camera, Matrix4f matrix4f, @NotNull PoseStack poseStack, Frustum frustum, float partialTicks) {
//        RenderStars(poseStack, matrix4f, partialTicks);
        assert Minecraft.getInstance().player != null;
        Vec3 position = Minecraft.getInstance().player.position();
        CustomLevelRenderer.renderImageSky(poseStack, END_SKY_LOCATION, new Vector4i(40, 40, 40, 40));
        renderPlanet(poseStack, new Vec3(20 - position.x, 200 - position.y, -20 - position.z), Axis.YP.rotation(Minecraft.getInstance().level.getSunAngle(partialTicks)), new Vec3(0, 0, 40), 1, "sun");
        RenderStars(poseStack);
}

    private static void RenderStars(@NotNull PoseStack poseStack) {
        RenderSystem.setShader(GameRenderer::getPositionShader);

        BufferBuilder buffer = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        poseStack.pushPose();
        float size = 0.5f;
        poseStack.translate(0, 100, 0);//((float) relativePosition.x, (float) relativePosition.y, (float) relativePosition.z);
        poseStack.scale(30.0F, 30.0F, 30.0F);

        Matrix4f matrix = poseStack.last().pose();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);

        for (int i = 0; i < 1000; ++i) {
            assert Minecraft.getInstance().level != null;
            float x = 190 - new Random(Minecraft.getInstance().level.getGameTime()).nextFloat() * (190 * 2);
            float z = 190 - new Random(Minecraft.getInstance().level.getGameTime()).nextFloat() * (190 * 2);
            float y = 190;
            buffer.vertex(matrix, x, y, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y, z).color(1F, 1, 1, 1).endVertex();

            //UP
            buffer.vertex(matrix, x - size, y + size, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z - size).color(1F, 1, 1, 1).endVertex();

            //East
            buffer.vertex(matrix, x, y, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y, z).color(1F, 1, 1, 1).endVertex();

            //West
            buffer.vertex(matrix, x - size, y, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y, z - size).color(1F, 1, 1, 1).endVertex();

            //SOUTH
            buffer.vertex(matrix, x - size, y, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y + size, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y + size, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y, z - size).color(1F, 1, 1, 1).endVertex();

            //Down
            buffer.vertex(matrix, x, y, z - size).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x, y, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y, z).color(1F, 1, 1, 1).endVertex();
            buffer.vertex(matrix, x - size, y, z - size).color(1F, 1, 1, 1).endVertex();

        }

        BufferUploader.drawWithShader(buffer.end());

        poseStack.popPose();
        RenderSystem.disableBlend();
    }
}
