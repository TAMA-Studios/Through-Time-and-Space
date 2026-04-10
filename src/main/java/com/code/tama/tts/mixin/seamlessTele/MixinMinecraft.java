/* (C) TAMA Studios 2026 */
package com.code.tama.tts.mixin.seamlessTele;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.Screen;

import com.code.tama.triggerapi.boti.teleporting.ClientSeamlessTeleportState;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

	@Unique private static final Logger LOGGER = LogManager.getLogger("TTS$SeamlessTele#Minecraft");

	@Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
	private void tts$suppressLoadingScreen(Screen screen, CallbackInfo ci) {
		boolean suppressing = ClientSeamlessTeleportState.isSuppressingLoadingScreen();

		LOGGER.info("[SMLS] setScreen — screen={}, suppressing={}",
				screen == null ? "null" : screen.getClass().getSimpleName(), suppressing);

		if (!suppressing)
			return;

		// Suppress the "Joining World" / "Downloading terrain" screens and their
		// dismissal (setScreen(null)) while a seamless teleport is in progress.
		if (screen == null || screen instanceof ReceivingLevelScreen || screen instanceof ProgressScreen) {
			LOGGER.info("[SMLS] setScreen SUPPRESSED for {}",
					screen == null ? "null" : screen.getClass().getSimpleName());
			ci.cancel();
		}
	}
}