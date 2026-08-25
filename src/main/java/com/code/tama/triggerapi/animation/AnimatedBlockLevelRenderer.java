/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.code.tama.triggerapi.TriggerAPI;

/**
 * LEVEL_EVENT backend: fires off AnimatedBlockRenderCore via Forge's stable
 * public RenderLevelStageEvent. Only active when AnimatedBlockConfig.MODE ==
 * LEVEL_EVENT, the mixin backend and this one are mutually exclusive so you
 * never draw the same block twice.
 */
@Mod.EventBusSubscriber(modid = TriggerAPI.MOD_ID, value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public class AnimatedBlockLevelRenderer {

	@SubscribeEvent
	public static void onRenderLevelStage(RenderLevelStageEvent event) {
		if (AnimatedBlockConfig.MODE != AnimatedBlockConfig.Mode.LEVEL_EVENT)
			return;
		if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS)
			return;

		var mc = Minecraft.getInstance();
		var level = mc.level;
		if (level == null)
			return;

		AnimatedBlockRenderCore.renderAll(level, event.getCamera().getPosition(), event.getPoseStack(),
				mc.renderBuffers().bufferSource(), event.getPartialTick());
	}
}
