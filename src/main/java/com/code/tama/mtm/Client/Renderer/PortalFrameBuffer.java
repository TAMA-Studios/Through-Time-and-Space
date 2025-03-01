package com.code.tama.mtm.Client.Renderer;

import com.mojang.blaze3d.pipeline.TextureTarget;
import net.minecraft.client.Minecraft;

public class PortalFrameBuffer {

    private static Minecraft mc = Minecraft.getInstance();
    private static TextureTarget portalFramebuffer;
    private static int lastWidth = 0, lastHeight = 0;

    public static void init(int width, int height) {
        if (portalFramebuffer != null) {
            // Prevent re-initialization unless size changes
            if (width == lastWidth && height == lastHeight) {
                return;
            }
            portalFramebuffer.destroyBuffers();
        }

        portalFramebuffer = new TextureTarget(width, height, true, false);
        lastWidth = width;
        lastHeight = height;
    }

    public static void bindFramebuffer() {
        if (portalFramebuffer != null && portalFramebuffer.width > 0 && portalFramebuffer.height > 0) {
            portalFramebuffer.bindWrite(true);
        }
    }

    public static void unbindFramebuffer() {
        mc.getMainRenderTarget().bindWrite(true);
    }

    public static TextureTarget getFramebuffer() {
        return portalFramebuffer;
    }

    public static void cleanup() {
        if (portalFramebuffer != null) {
            portalFramebuffer.destroyBuffers();
            portalFramebuffer = null;
        }
    }
}