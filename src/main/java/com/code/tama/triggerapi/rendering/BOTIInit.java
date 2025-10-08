package com.code.tama.triggerapi.rendering;

import com.code.tama.triggerapi.botiutils.IHelpWithFBOs;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;

public class BOTIInit {
    public RenderTarget fbo;
    public LevelRenderer worldRenderer;

    public void setupFramebuffer() {

        Window res = Minecraft.getInstance().getWindow();

        if(Minecraft.getInstance().level.getGameTime() % 40 == 1)
        fbo = null;

        if(fbo != null && (fbo.width != res.getWidth() || fbo.height != res.getHeight()))
            fbo.resize(Minecraft.getInstance().getMainRenderTarget().viewWidth, Minecraft.getInstance().getMainRenderTarget().viewHeight, Minecraft.ON_OSX);

        if(fbo == null) {
            fbo = new TextureTarget(Minecraft.getInstance().getMainRenderTarget().viewWidth, Minecraft.getInstance().getMainRenderTarget().viewHeight, true, Minecraft.ON_OSX);
        }

        if(!fbo.isStencilEnabled()) fbo.enableStencil();
        if(!((IHelpWithFBOs) Minecraft.getInstance().getMainRenderTarget()).getIsStencilBufferEnabled())
            ((IHelpWithFBOs) Minecraft.getInstance().getMainRenderTarget()).setIsStencilBufferEnabledAndReload(true);

        fbo.bindWrite(false);
        fbo.checkStatus();
    }

    public void endFBO() {
        fbo.unbindWrite();
        fbo.clear(Minecraft.ON_OSX);
    }


}
