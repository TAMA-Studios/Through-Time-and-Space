/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class TOCPage extends Page {

	private final List<NamedAction> actions = new ArrayList<>();
	private final ManualScreen manual;
	private int maxIndex = 0;
	private int minIndex = 0;
	public static final int MAX_LINES = 13;

	public TOCPage(ManualScreen manual, int minIndex, int maxIndex) {
		this.manual = manual;
		this.minIndex = minIndex;
		this.maxIndex = maxIndex;
		this.addButtons(manual);
	}

	public void addButtons(ManualScreen screen) {
		int index = 0;
		if (this.maxIndex < screen.getChapters().size() && this.minIndex < screen.getChapters().size()) {
			for (int chapterIndex = this.minIndex; chapterIndex <= this.maxIndex;) {
				ManualChapter manualChapter = screen.getChapters().get(chapterIndex);
				if (manualChapter != null) {
					if (!manualChapter.getDisplayName().isEmpty()) {
						final int finalIndex = chapterIndex;
						// Use Minecraft.getInstance().font.lineHeight
						this.actions.add(new NamedAction(manualChapter.getDisplayName(), () -> {
							screen.openPage(finalIndex, 0);
						}, Minecraft.getInstance().font.lineHeight * index));
						++index;
					}
					chapterIndex++;
				}
			}
		}
	}

	@Override
	public void render(GuiGraphics guiGraphics, Font font, int globalPage, int x, int y, int width, int height) {
		// In 1.20, we draw via guiGraphics.
		// font.draw is replaced by guiGraphics.drawString
		guiGraphics.drawString(font, Component.literal("Table of Contents").withStyle(ChatFormatting.BOLD), x, y - 10,
				0x000000, false);

		for (int i = 0; i < this.actions.size(); ++i) {
			NamedAction action = this.actions.get(i);
			guiGraphics.drawString(font, Component.literal(action.getName()), x, y + action.y + font.lineHeight - 5,
					0x463316, false);
		}
	}

	@Override
	public void onClick(double x, double y) {
		for (NamedAction action : this.actions) {
			// Note: Ensure your local Y coordinate calculation matches the screen-space
			// logic used in 1.16
			if (action.isInBounds((int) Math.floor(y), Minecraft.getInstance().font.lineHeight)) {
				action.act();
				return;
			}
		}
	}

	public static class NamedAction {
		private final String name;
		private final Runnable action;
		private final int y;

		public NamedAction(String name, Runnable action, int y) {
			this.name = name;
			this.action = action;
			this.y = y;
		}

		public String getName() {
			return name;
		}

		public void act() {
			this.action.run();
		}

		public boolean isInBounds(int y, int fontHeight) {
			return y > this.y && y < this.y + fontHeight;
		}
	}
}