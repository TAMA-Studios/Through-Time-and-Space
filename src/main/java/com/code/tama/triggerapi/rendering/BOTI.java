package com.code.tama.triggerapi.rendering;


import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11;


public class BOTI {
    public static void copyRenderTarget(RenderTarget src, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height, GlConst.GL_DEPTH_BUFFER_BIT | GlConst.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static void copyColor(RenderTarget src, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height, GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static void copyDepth(RenderTarget src, RenderTarget dest) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, src.frameBufferId);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, dest.frameBufferId);
        GlStateManager._glBlitFrameBuffer(0, 0, src.width, src.height, 0, 0, dest.width, dest.height, GlConst.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT, GlConst.GL_NEAREST);
    }

    public static void setRenderTargetColor(RenderTarget src, float r, float g, float b, float a) {
        src.setClearColor(r, g, b, a);
    }
}
