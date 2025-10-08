package com.code.tama.triggerapi.rendering;

import com.code.tama.triggerapi.botiutils.BOTIUtils;
import com.code.tama.triggerapi.botiutils.IHelpWithFBOs;
import com.code.tama.tts.TTSMod;
import com.code.tama.tts.mixin.client.RenderStateShardAccessor;
import com.code.tama.tts.server.tileentities.AbstractPortalTile;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import static com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS;

public class RenderTargetHelper {

    private static final RenderTargetHelper RENDER_TARGET_HELPER = new RenderTargetHelper();
    public static StencilBufferStorage stencilBufferStorage = new StencilBufferStorage();
    public RenderTarget renderTarget;


    public static Logger LOGGER = LogManager.getLogger("TardisRefinbed/StencilRendering");

    private static final ResourceLocation BLACK = new ResourceLocation(TTSMod.MODID, "textures/black.png");


    public static void checkGLError(String msg) {
        int error;
        while ((error = GL11.glGetError()) != GL11.GL_NO_ERROR) {
            LOGGER.debug("{}: {}", msg, error);
        }

    }

    public static void Render(AbstractPortalTile blockEntity, PoseStack stack, int packedLight, MultiBufferSource source) {
        if (ModList.get().isLoaded("immersive_portals")) {
            return; // Don't even risk it
        }


        if(!((IHelpWithFBOs) Minecraft.getInstance().getMainRenderTarget()).tts$IsStencilBufferEnabled()) {
            RenderTarget renderTarget1 = Minecraft.getInstance().getMainRenderTarget();
            ((IHelpWithFBOs) renderTarget1).tts$SetStencilBufferEnabled(true);
        }

        stack.pushPose();

        stack.translate(0, 0, -0.5);

        RenderSystem.depthMask(true);

        MultiBufferSource.BufferSource imBuffer = stencilBufferStorage.getVertexConsumer();
        // TODO: Render Door Frame RIGHT HERE (implement datapack door frames, BOTI mask named "BOTI")

        imBuffer.endBatch();

        // Enable and configure stencil buffer
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);

        // Render mask
        RenderSystem.depthMask(true);
        stack.pushPose();
        BotiPortalModel.createBodyLayer().bakeRoot().render(stack, imBuffer.getBuffer(RenderType.entityTranslucentCull(BLACK)), packedLight, OverlayTexture.NO_OVERLAY, 0, 0, 0, 1f);
        imBuffer.endBatch();
        stack.popPose();
        RenderSystem.depthMask(false);

        // Render BOTI using stencil buffer
        GL11.glStencilMask(0x00);
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
        GlStateManager._depthFunc(GL11.GL_ALWAYS);

        GL11.glColorMask(true, true, true, false);
        stack.pushPose();
        stack.scale(10, 10, 10);

        BOTIUtils.RenderStuff(stack, blockEntity, source);

        stack.popPose();

        GlStateManager._depthFunc(GL11.GL_LEQUAL);
        GL11.glColorMask(true, true, true, true);

        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        RenderSystem.depthMask(true);


        stack.popPose();
    }

    public void start() {
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_DEBUG, GLFW.GLFW_TRUE);


        Window window = Minecraft.getInstance().getWindow();
        int width = window.getWidth();
        int height = window.getHeight();

        // Check if renderTarget needs to be reinitialized
        if (renderTarget == null || renderTarget.width != width || renderTarget.height != height) {
            renderTarget = new TextureTarget(width, height, true, Minecraft.ON_OSX);
        }

        renderTarget.bindWrite(false);
        renderTarget.checkStatus();

        if (!((IHelpWithFBOs) renderTarget).tts$IsStencilBufferEnabled()) {
            ((IHelpWithFBOs) renderTarget).tts$SetStencilBufferEnabled(true);
        }
    }


    public void end(boolean clear) {
        renderTarget.clear(clear);
        renderTarget.unbindWrite();
    }

    @OnlyIn(Dist.CLIENT) @SuppressWarnings("deprecation")
    public static class StencilBufferStorage extends RenderBuffers {


        private final Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> typeBufferBuilder =
                Util.make(new Object2ObjectLinkedOpenHashMap<>(), map -> put(map, getConsumer()));
        private final MultiBufferSource.BufferSource consumer = MultiBufferSource.immediateWithBuffers(typeBufferBuilder, new BufferBuilder(256));

        public static RenderType getConsumer() {
            RenderType.CompositeState parameters = RenderType.CompositeState.builder()
                    .setTextureState(RenderStateShardAccessor.getBLOCK_SHEET_MIPPED())
                    .setTransparencyState(RenderStateShardAccessor.getTRANSLUCENT_TRANSPARENCY())
                    .setLayeringState(RenderStateShardAccessor.getNO_LAYERING()).createCompositeState(false);
            return RenderType.create("boti", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
                    QUADS, 256, false, true, parameters);
        }

        private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> builderStorage, RenderType layer) {
            builderStorage.put(layer, new BufferBuilder(layer.bufferSize()));
        }

        public MultiBufferSource.BufferSource getVertexConsumer() { // idk why but lombok won't work for this
            return this.consumer;
        }
    }
}