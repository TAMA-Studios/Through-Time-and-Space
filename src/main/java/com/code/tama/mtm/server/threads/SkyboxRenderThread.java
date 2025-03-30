package com.code.tama.mtm.server.threads;

import com.code.tama.mtm.server.capabilities.CapabilityConstants;
import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.client.CustomLevelRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;

public class SkyboxRenderThread extends Thread {
    boolean IsInitted = false;
    RenderLevelStageEvent event;

    public SkyboxRenderThread() {}

    public void SetEvent(RenderLevelStageEvent event) {
        this.event = event;
    }

    @Override
    public void run() {
        super.run();
        if(this.event == null) return;
        // Make sure it's only rendering AFTER the sky
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            // Get each renderer
            CustomLevelRenderer.Renderers.forEach(renderer -> {
                // Run the Render code in each renderer
                renderer.Render(event.getCamera(), event.getProjectionMatrix(), event.getPoseStack(), event.getFrustum(), event.getPartialTick());
            });
        }

        // Calculate the light level from the cap if it exists
        float ambientLight = Minecraft.getInstance().level.getCapability(CapabilityConstants.TARDIS_LEVEL_CAPABILITY)
                .map(ITARDISLevel::GetLightLevel).orElse(1.0f);//0.5f;  // Default value

        // Apply the calculated lighting
        CustomLevelRenderer.applyLighting(ambientLight);
        CustomLevelRenderer.applyFogEffect(ambientLight);

        // This is to force it to wait for the next tick when it gets set again
        this.event = null;
    }
}