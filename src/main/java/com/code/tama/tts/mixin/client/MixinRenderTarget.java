package com.code.tama.tts.mixin.client;

import com.code.tama.triggerapi.UtilityGL11Debug;
import com.code.tama.triggerapi.botiutils.IHelpWithFBOs;
import com.code.tama.triggerapi.botiutils.IPCGlobal;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL30C;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL30.*;

@Mixin(RenderTarget.class)
public abstract class MixinRenderTarget implements IHelpWithFBOs {
    
    @Unique
    private boolean throughTimeAndSpace$isStencilBufferEnabled;
    
    @Shadow
    public int width;
    @Shadow
    public int height;

    @Shadow
    public abstract void clear(boolean getError);

    @Shadow public abstract void resize(int width, int height, boolean clearError);
    
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(
        boolean useDepth,
        CallbackInfo ci
    ) {
        throughTimeAndSpace$isStencilBufferEnabled = false;
    }

    @Redirect(
        method = "createBuffers(IIZ)V",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/platform/GlStateManager;_texImage2D(IIIIIIIILjava/nio/IntBuffer;)V"
        )
    )
    private void redirectTexImage2d(
        int target, int level, int internalFormat,
        int width, int height,
        int border, int format, int type,
        IntBuffer pixels
    ) {
        if (internalFormat == GL_DEPTH_COMPONENT && throughTimeAndSpace$isStencilBufferEnabled) {
            GlStateManager._texImage2D(
                target,
                level,
                IPCGlobal.useAnotherStencilFormat ? GL_DEPTH32F_STENCIL8 : GL_DEPTH24_STENCIL8,
                width,
                height,
                border,
                ARBFramebufferObject.GL_DEPTH_STENCIL,
                IPCGlobal.useAnotherStencilFormat ? GL_FLOAT_32_UNSIGNED_INT_24_8_REV : GL30.GL_UNSIGNED_INT_24_8,
                pixels
            );
        }
        else {
            GlStateManager._texImage2D(
                target, level, internalFormat, width, height,
                border, format, type, pixels
            );
        }
    }

    @Redirect(
        method = "createBuffers(IIZ)V",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/platform/GlStateManager;_glFramebufferTexture2D(IIIII)V"
        )
    )
    private void redirectFrameBufferTexture2d(
        int target, int attachment, int textureTarget, int texture, int level
    ) {

        if (attachment == GL30C.GL_DEPTH_ATTACHMENT && throughTimeAndSpace$isStencilBufferEnabled) {
            GlStateManager._glFramebufferTexture2D(
                target, GL30.GL_DEPTH_STENCIL_ATTACHMENT, textureTarget, texture, level
            );
        }
        else {
            GlStateManager._glFramebufferTexture2D(target, attachment, textureTarget, texture, level);
        }
    }
    
    @Inject(
        method = "copyDepthFrom(Lcom/mojang/blaze3d/pipeline/RenderTarget;)V",
        at = @At("RETURN")
    )
    private void onCopiedDepthFrom(RenderTarget framebuffer, CallbackInfo ci) {
        UtilityGL11Debug.dumpAllIsEnabled();
    }
    
    @Override
    public boolean getIsStencilBufferEnabled() {
        return throughTimeAndSpace$isStencilBufferEnabled;
    }
    
    @Override
    public void setIsStencilBufferEnabledAndReload(boolean cond) {
        if (throughTimeAndSpace$isStencilBufferEnabled != cond) {
            throughTimeAndSpace$isStencilBufferEnabled = cond;
            resize(width, height, Minecraft.ON_OSX);
        }
    }
}