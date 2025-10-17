/* (C) TAMA Studios 2025 */
package com.code.tama.tts.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Timer;
import net.minecraft.client.renderer.LevelRenderer;

@Mixin(Minecraft.class)
public interface IMinecraftAccessor {
	@Accessor
	Timer getTimer();

	@Mutable
	@Accessor("levelRenderer")
	void setLevelRenderer(LevelRenderer renderer);
}
