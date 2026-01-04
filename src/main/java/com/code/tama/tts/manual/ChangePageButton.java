/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import org.jetbrains.annotations.NotNull;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChangePageButton extends Button {
	public static final ResourceLocation BOOK_LOCATION = new ResourceLocation("textures/gui/book.png");
	private final boolean playTurnSound;

	public ChangePageButton(int x, int y, boolean idfk, Button.OnPress press, boolean playTurnSound) {
		super(x, y, 23, 13, Component.empty(), press, DEFAULT_NARRATION);
		this.playTurnSound = playTurnSound;
	}

	@Override
	public void playDownSound(@NotNull SoundManager soundManager) {
		if (this.playTurnSound) {
			soundManager.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
		}
	}
}
