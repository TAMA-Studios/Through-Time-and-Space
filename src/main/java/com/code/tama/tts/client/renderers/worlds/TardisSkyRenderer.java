/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.worlds;

import com.code.tama.tts.client.renderers.worlds.helper.AbstractLevelRenderer;
import com.code.tama.tts.server.worlds.dimension.MDimensions;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexBuffer;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class TardisSkyRenderer extends AbstractLevelRenderer {
    private static VertexBuffer StarsVBO = null;
    private static VertexBuffer SunVBO = null;

    @Override
    public ResourceLocation EffectsLocation() {
        return MDimensions.TARDIS_DIM_TYPE.location();
    }

    @Override
    public void RenderLevel(
            @NotNull Camera camera,
            Matrix4f matrix4f,
            @NotNull PoseStack poseStack,
            Frustum frustum,
            float partialTicks) {
        //        assert Minecraft.getInstance().player != null;
        //        Vec3 position = Minecraft.getInstance().player.position();
        //        //        CustomLevelRenderer.renderImageSky(poseStack, END_SKY_LOCATION, new
        // Vector4i(40, 40, 40,
        // 40));
        //        assert Minecraft.getInstance().level != null;
        //        renderSun(
        //                poseStack,
        //                matrix4f,
        //                new Vec3(20 - position.x, 200 - position.y, -20 - position.z),
        //                Axis.YP.rotation(Minecraft.getInstance().level.getSunAngle(partialTicks)),
        //                new Vec3(0, 0, 40),
        //                1);
        //
        //        RenderStars(poseStack, matrix4f);
    }

    @Override
    public boolean ShouldRenderVoid() {
        return false;
    }
}
