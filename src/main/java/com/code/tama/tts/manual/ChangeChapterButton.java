/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public class ChangeChapterButton extends Button {

	protected final boolean isForward;
	protected final boolean playTurnSound;

	public ChangeChapterButton(int x, int y, int width, int height, boolean isForward, Button.OnPress onPress,
			boolean playTurnSound) {
		super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);
		this.isForward = isForward;
		this.playTurnSound = playTurnSound;
	}

	public ChangeChapterButton(int x, int y, boolean isForward, Button.OnPress onPress, boolean playTurnSound) {
		this(x, y, 17, 12, isForward, onPress, playTurnSound);
	}

	@Override
	protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		RenderSystem.setShaderTexture(0, ManualScreen.TEXTURE);

		int u = 5;
		int v = 228;

		if (this.isHovered()) {
			u += 22;
		}

		if (!this.isForward) {
			v += 14;
		}

		guiGraphics.blit(ManualScreen.TEXTURE, this.getX(), this.getY(), u, v, this.width, this.height);
	}

	@Override
	public void playDownSound(@NotNull SoundManager soundManager) {
		if (this.playTurnSound) {
			soundManager.play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
		}
	}
}
