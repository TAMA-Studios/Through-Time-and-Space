/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;

import com.code.tama.triggerapi.universal.UniversalCommon;

public class TTSPartialModels {
	public static final PartialModel MODEL = block("model");

	private static PartialModel block(String path) {
		return PartialModel.of(UniversalCommon.modRL("block/" + path));
	}
}
