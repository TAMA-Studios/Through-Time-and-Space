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

	@Inject(method = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At("HEAD"), cancellable = true)
	private void tts$suppressLoadingScreen(Screen screen, CallbackInfo ci) {
		if (screen == null)
			return;

		boolean suppressing = ClientSeamlessTeleportState.isSuppressingLoadingScreen()
				&& (screen instanceof ReceivingLevelScreen || screen instanceof ProgressScreen);

		LOGGER.info("[SMLS] setScreen — screen={}, suppressing={}", screen.getClass().getSimpleName(), suppressing);

		if (!suppressing)
			return;

		LOGGER.info("[SMLS] setScreen SUPPRESSED for {}", screen.getClass().getSimpleName());
		ci.cancel();
	}
}