/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import static org.lwjgl.opengl.GL30.*;

import com.code.tama.triggerapi.botiutils.IHelpWithFBOs;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.MainTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import java.nio.IntBuffer;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL30C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MainTarget.class)
public abstract class MixinMainTarget extends RenderTarget {

    public MixinMainTarget(boolean useDepth) {
        super(useDepth);
        throw new RuntimeException();
    }

    @WrapOperation(
            method = "allocateDepthAttachment",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lcom/mojang/blaze3d/platform/GlStateManager;_texImage2D(IIIIIIIILjava/nio/IntBuffer;)V",
                            remap = false))
    private void modifyTexImage2D(
            int pTarget,
            int pLevel,
            int pInternalFormat,
            int pWidth,
            int pHeight,
            int pBorder,
            int pFormat,
            int pType,
            IntBuffer pPixels,
            Operation<Void> original) {
        boolean isStencilBufferEnabled = ((IHelpWithFBOs) this).tts$IsStencilBufferEnabled();

        if (isStencilBufferEnabled) {
            pInternalFormat = false ? GL_DEPTH32F_STENCIL8 : GL_DEPTH24_STENCIL8;
            pFormat = ARBFramebufferObject.GL_DEPTH_STENCIL;
            pType = false ? GL_FLOAT_32_UNSIGNED_INT_24_8_REV : GL30C.GL_UNSIGNED_INT_24_8;
        }

        original.call(pTarget, pLevel, pInternalFormat, pWidth, pHeight, pBorder, pFormat, pType, pPixels);
    }

    @WrapOperation(
            method = "createFrameBuffer",
            at =
                    @At(
                            value = "INVOKE",
                            target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glFramebufferTexture2D(IIIII)V",
                            remap = false))
    private void modifyFrameBufferTexture2d(
            int pTarget, int pAttachment, int pTexTarget, int pTexture, int pLevel, Operation<Void> original) {
        boolean isStencilBufferEnabled = ((IHelpWithFBOs) this).tts$IsStencilBufferEnabled();

        if (isStencilBufferEnabled) {
            if (pAttachment == GL30.GL_DEPTH_ATTACHMENT) {
                pAttachment = GL30.GL_DEPTH_STENCIL_ATTACHMENT;
            }
        }

        original.call(pTarget, pAttachment, pTexTarget, pTexture, pLevel);
    }
}
