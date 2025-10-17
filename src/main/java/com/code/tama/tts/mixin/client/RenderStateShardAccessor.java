/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.renderer.RenderStateShard;

@Mixin(RenderStateShard.class)
public interface RenderStateShardAccessor {
	@Accessor
	static RenderStateShard.TextureStateShard getBLOCK_SHEET_MIPPED() {
		throw new AssertionError();
	}

	@Accessor
	static RenderStateShard.LayeringStateShard getNO_LAYERING() {
		throw new AssertionError();
	}

	@Accessor
	static RenderStateShard.TransparencyStateShard getTRANSLUCENT_TRANSPARENCY() {
		throw new AssertionError();
	}
}
