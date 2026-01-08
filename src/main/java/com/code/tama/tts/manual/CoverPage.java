/* (C) TAMA Studios 2026 */
package com.code.tama.tts.manual;

import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class CoverPage extends Page {

	private ResourceLocation texture;
	@Getter
	private String title;

	@Override
	public void render(GuiGraphics guiGraphics, Font font, int globalPage, int x, int ny, int width, int height) {

		// Draw title
		int i = 0;
		for (String title : this.getLines()) {
			int titleSpace = WIDTH / 4;
			guiGraphics.drawString(font, title, x + titleSpace, ny + ((font.lineHeight + 2) * i) - 5, 0x00000);
			++i;
		}

		// Draw icon
		if (this.texture != null) {
			RenderSystem.enableBlend();

			int w = WIDTH, h = WIDTH;
			int iconX = x + (int) Math.floor(w / 2.0) - 10;
			int iconY = ny + h / 2;

			// Use GuiGraphics blit method for texture rendering
			guiGraphics.blit(this.texture, iconX, iconY, 0, 0, w, h, w, h);

			RenderSystem.disableBlend();
		}
	}

	public void setIcon(ResourceLocation loc) {
		this.texture = new ResourceLocation(loc.getNamespace(), "textures/manual/" + loc.getPath() + ".png");
	}

	public void setTitle(String title) {
		this.title = title;
		this.parseString(title);
	}

}