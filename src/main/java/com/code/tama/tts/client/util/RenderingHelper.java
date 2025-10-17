/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RenderingHelper {
	@OnlyIn(Dist.CLIENT)
	public static BlockEntityRendererProvider.Context GetTileRendererContext() {
		Minecraft mc = Minecraft.getInstance();

		return new BlockEntityRendererProvider.Context(mc.getBlockEntityRenderDispatcher(), mc.getBlockRenderer(),
				mc.getItemRenderer(), mc.getEntityRenderDispatcher(), mc.getEntityModels(), mc.font);
	}
}
