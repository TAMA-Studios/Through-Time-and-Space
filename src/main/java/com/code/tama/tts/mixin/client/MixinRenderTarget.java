/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL30.*;

import com.code.tama.triggerapi.botiutils.IHelpWithFBOs;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlUtil;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL30C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderTarget.class)
public abstract class MixinRenderTarget implements IHelpWithFBOs {

    @Unique private boolean tts$isStencilEnabled = false;

    @Redirect(
            method = "createBuffers(IIZ)V",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lcom/mojang/blaze3d/platform/GlStateManager;_texImage2D(IIIIIIIILjava/nio/IntBuffer;)V"))
    private void redirectTex(
            int target,
            int level,
            int internalFormat,
            int width,
            int height,
            int border,
            int format,
            int type,
            IntBuffer pixels) {
        if (internalFormat == GL_DEPTH_COMPONENT && tts$isStencilEnabled)
            GlStateManager._texImage2D(
                    target,
                    level,
                    !GlUtil.getVendor().toLowerCase().contains("nvidia") ? GL_DEPTH32F_STENCIL8 : GL_DEPTH24_STENCIL8,
                    width,
                    height,
                    border,
                    ARBFramebufferObject.GL_DEPTH_STENCIL,
                    !GlUtil.getVendor().toLowerCase().contains("nvidia")
                            ? GL_FLOAT_32_UNSIGNED_INT_24_8_REV
                            : GL30.GL_UNSIGNED_INT_24_8,
                    pixels);
        else GlStateManager._texImage2D(target, level, internalFormat, width, height, border, format, type, pixels);
    }

    @Redirect(
            method = "createBuffers(IIZ)V",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glFramebufferTexture2D(IIIII)V"))
    private void redirectFBOTex(int target, int attachment, int textureTarget, int texture, int level) {
        if (attachment == GL30C.GL_DEPTH_ATTACHMENT && tts$isStencilEnabled)
            GlStateManager._glFramebufferTexture2D(
                    target, GL30.GL_DEPTH_STENCIL_ATTACHMENT, textureTarget, texture, level);
        else GlStateManager._glFramebufferTexture2D(target, attachment, textureTarget, texture, level);
    }

    @Override
    public void tts$SetStencilBufferEnabled(boolean cond) {
        if (tts$isStencilEnabled != cond) {
            tts$isStencilEnabled = cond;
            ((RenderTarget) (Object) this)
                    .resize(
                            ((RenderTarget) (Object) this).width,
                            ((RenderTarget) (Object) this).height,
                            Minecraft.ON_OSX);
        }
    }

    @Override
    public boolean tts$IsStencilBufferEnabled() {
        return tts$isStencilEnabled;
    }
}
