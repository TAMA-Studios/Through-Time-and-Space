/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import com.code.tama.tts.TTSMod;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;

import net.minecraft.resources.ResourceLocation;

public class TTSPartialModels {
	public static final PartialModel MODEL = block("model");

	private static PartialModel block(String path) {
		return PartialModel.of(new ResourceLocation(TTSMod.MODID, "block/" + path));
	}
}
