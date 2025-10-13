/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.renderers.tiles;

import com.code.tama.tts.TTSMod;
import com.code.tama.tts.server.tileentities.ChromiumBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class ChromiumBlockEntityRenderer implements BlockEntityRenderer<ChromiumBlockEntity> {

    public ChromiumBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(
            ChromiumBlockEntity pBlockEntity,
            float pPartialTick,
            PoseStack pPoseStack,
            MultiBufferSource pBufferSource,
            int pPackedLight,
            int pPackedOverlay) {
        // This is where your custom rendering logic goes.

        // Get the sprite for the block (your chromium texture)
        // You'll need to define this texture in your assets
        TextureAtlasSprite texture = Minecraft.getInstance()
                .getTextureAtlas(TextureAtlas.LOCATION_BLOCKS)
                .apply(new ResourceLocation(TTSMod.MODID, "block/chromium_block"));

        // Bind your custom shader
        //        ShadersUtil.CHROMIUM_SHADER.apply();

        // Pass uniforms to your shader (e.g., camera position, light data)
        // Minecraft automatically passes many uniforms, but custom ones need to be handled.
        // For a simple effect, you might not need to pass much explicitly beyond what vanilla does.
        // You'll need to access RenderSystem.getProjectionMatrix(), RenderSystem.getModelViewMatrix(),
        // etc.
        // However, with custom shaders, you're responsible for setting up matrices if you want them.
        // For this simple example, we'll rely on the default matrix setup provided by Minecraft's
        // rendering context.

        pPoseStack.pushPose();
        // Translate to block position
        // pPoseStack.translate(pBlockEntity.getBlockPos().getX(), pBlockEntity.getBlockPos().getY(),
        // pBlockEntity.getBlockPos().getZ());

        // You'll need to draw quads manually or load a custom model here.
        // For simplicity, let's assume you're drawing a simple cube.
        // This is the tricky part: vanilla rendering usually involves BakedModels.
        // For a custom shader, you often draw vertices directly.

        // For a basic example, we'll try to get the vanilla block model and render it
        // but with our shader active. This might be tricky because the vanilla model
        // is designed for the vanilla shader.
        // A better approach is to render a simple quad or cube manually with your shader.

        // Example of manually drawing a textured quad (one face of the block):
        // This is highly simplified and assumes an OpenGL-like direct draw.
        // In modern Minecraft, you use VertexConsumers.

        // Instead of directly drawing, you'd likely use a VertexConsumer.
        // The standard way is to get a buffer from pBufferSource.
        // This example is illustrative; actual vertex drawing is verbose.
        // You'd need to create a `VertexConsumer` and add vertices with position, color, UV, normal,
        // light.

        // THIS IS PSEUDOCODE FOR MANUAL CUBE RENDERING:
        /*
        VertexConsumer consumer = pBufferSource.getBuffer(ModShaders.CHROMIUM_RENDER_TYPE); // Need a custom RenderType
        Matrix4f modelViewMatrix = pPoseStack.last().pose();
        Matrix3f normalMatrix = pPoseStack.last().normal();

        // Face 1 (e.g., +X face)
        float x1 = 1.0f, y1 = 0.0f, z1 = 0.0f;
        float x2 = 1.0f, y2 = 1.0f, z2 = 0.0f;
        float x3 = 1.0f, y3 = 1.0f, z3 = 1.0f;
        float x4 = 1.0f, y4 = 0.0f, z4 = 1.0f;

        // Normals for +X face
        float nx = 1.0f, ny = 0.0f, nz = 0.0f;

        // Add vertices for a quad
        consumer.vertex(modelViewMatrix, x1, y1, z1).color(1.0f, 1.0f, 1.0f, 1.0f).uv(texture.getU0(), texture.getV0()).overlayCoords(pPackedOverlay).uv2(pPackedLight).normal(normalMatrix, nx, ny, nz).endVertex();
        consumer.vertex(modelViewMatrix, x2, y2, z2).color(1.0f, 1.0f, 1.0f, 1.0f).uv(texture.getU0(), texture.getV1()).overlayCoords(pPackedOverlay).uv2(pPackedLight).normal(normalMatrix, nx, ny, nz).endVertex();
        consumer.vertex(modelViewMatrix, x3, y3, z3).color(1.0f, 1.0f, 1.0f, 1.0f).uv(texture.getU1(), texture.getV1()).overlayCoords(pPackedOverlay).uv2(pPackedLight).normal(normalMatrix, nx, ny, nz).endVertex();
        consumer.vertex(modelViewMatrix, x4, y4, z4).color(1.0f, 1.0f, 1.0f, 1.0f).uv(texture.getU1(), texture.getV0()).overlayCoords(pPackedOverlay).uv2(pPackedLight).normal(normalMatrix, nx, ny, nz).endVertex();
        // Repeat for all 6 faces...
        */

        // For a block, the easiest is to make a BlockModel JSON that points to a BlockEntityRenderer.
        // This means your block's `blockstates` JSON would look something like:
        // { "variants": { "": { "model": "yourmod:block/chromium_block_renderer" } } }
        // And your `chromium_block_renderer.json` in models/block would simply have:
        // { "loader": "forge:obj", "model": "yourmod:models/block/chromium_model.obj" }
        // Or if you want to bypass OBJ/JSON and render directly, you need a custom model loader.
        // This path is complex. A more common approach for simple custom rendering:
        // Render a vanilla model, but with a custom render type and shader.

        // Instead of custom drawing, we'll try to get the vanilla model to render with our shader.
        // This is complex because you need to override the RenderType and the shader program used by
        // the vanilla model
        // baker.
        // Minecraft's rendering usually bakes models into vertex data using a specific RenderType.
        // To use a custom shader, you need a custom RenderType.

        BlockState state = pBlockEntity.getBlockState();
        // This will attempt to get and render the default block model for your block.
        // We'll then try to hook into the shader system later.
        Minecraft.getInstance()
                .getBlockRenderer()
                .renderSingleBlock(state, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay);

        // Reset to default shader if necessary (usually not needed if RenderSystem.setShader is used
        // later)
        // RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
        pPoseStack.popPose();
    }
}
