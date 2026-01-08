/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.code.tama.tts.server.networking.Networking;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import com.code.tama.triggerapi.networking.gui.ButtonClickPacket;

public class CustomGuiScreen extends Screen {
	private final GuiDefinition definition;
	private final ResourceLocation guiId;
	private int leftPos;
	private int topPos;
	private GuiDefinition.GuiElement hoveredElement = null;

	public CustomGuiScreen(GuiDefinition definition, ResourceLocation guiId) {
		super(Component.literal(definition.getTitle() != null ? definition.getTitle() : "GUI"));
		this.definition = definition;
		this.guiId = guiId;
	}

	@Override
	protected void init() {
		super.init();
		this.leftPos = (this.width - definition.getWidth()) / 2;
		this.topPos = (this.height - definition.getHeight()) / 2;
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(graphics);

		// Render background texture
		if (definition.getBackgroundTexture() != null) {
			try {
				ResourceLocation bgTexture = new ResourceLocation(definition.getBackgroundTexture());
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.setShaderTexture(0, bgTexture);

				graphics.blit(bgTexture, leftPos, topPos, 0, 0, definition.getWidth(), definition.getHeight(),
						definition.getWidth(), definition.getHeight());
			} catch (Exception e) {
				// Draw default background if texture fails
				graphics.fill(leftPos, topPos, leftPos + definition.getWidth(), topPos + definition.getHeight(),
						0xFF8B8B8B);
			}
		} else {
			// Default gray background
			graphics.fill(leftPos, topPos, leftPos + definition.getWidth(), topPos + definition.getHeight(),
					0xFF8B8B8B);
		}

		hoveredElement = null;

		// Render elements
		if (definition.getElements() != null) {
			for (GuiDefinition.GuiElement element : definition.getElements()) {
				renderElement(graphics, element, mouseX, mouseY);
			}
		}

		// Render tooltips
		if (hoveredElement != null && hoveredElement.getTooltip() != null) {
			List<Component> tooltip = new ArrayList<>();
			for (String line : hoveredElement.getTooltip()) {
				tooltip.add(Component.literal(line));
			}
			graphics.renderTooltip(this.font, tooltip, Optional.empty(), mouseX, mouseY);
		}

		super.render(graphics, mouseX, mouseY, partialTick);
	}

	private void renderElement(GuiGraphics graphics, GuiDefinition.GuiElement element, int mouseX, int mouseY) {
		int x = leftPos + element.getX();
		int y = topPos + element.getY();

		boolean hovered = mouseX >= x && mouseX < x + element.getWidth() && mouseY >= y
				&& mouseY < y + element.getHeight();

		if (hovered && element.getTooltip() != null) {
			hoveredElement = element;
		}

		switch (element.getType()) {
			case "button" -> renderButton(graphics, element, x, y, hovered);
			case "text" -> renderText(graphics, element, x, y);
			case "image" -> renderImage(graphics, element, x, y);
			case "item" -> renderItem(graphics, element, x, y);
		}
	}

	private void renderButton(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y, boolean hovered) {
		if (element.getTexture() != null) {
			try {
				ResourceLocation texture = new ResourceLocation(element.getTexture());
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.setShaderTexture(0, texture);

				int textureY = hovered && element.getHoverTextureY() != 0
						? element.getHoverTextureY()
						: element.getTextureY();

				graphics.blit(texture, x, y, element.getTextureX(), textureY, element.getWidth(), element.getHeight(),
						element.getImageWidth(), element.getImageHeight());
			} catch (Exception e) {
				// Fallback to colored rectangle
				int color = hovered ? 0xFFFFFFFF : 0xFFAAAAAA;
				graphics.fill(x, y, x + element.getWidth(), y + element.getHeight(), color);
			}
		} else {
			// Default button appearance
			int color = hovered ? 0xFFFFFFFF : 0xFFAAAAAA;
			graphics.fill(x, y, x + element.getWidth(), y + element.getHeight(), color);
		}
	}

	private void renderText(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y) {
		if (element.getText() != null) {
			PoseStack poseStack = graphics.pose();
			poseStack.pushPose();
			poseStack.translate(x, y, 0);
			poseStack.scale(element.getScale(), element.getScale(), 1.0f);

			if (element.hasShadow()) {
				graphics.drawString(this.font, element.getText(), 0, 0, element.getColor(), true);
			} else {
				graphics.drawString(this.font, element.getText(), 0, 0, element.getColor(), false);
			}

			poseStack.popPose();
		}
	}

	private void renderImage(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y) {
		if (element.getTexture() != null) {
			try {
				ResourceLocation texture = new ResourceLocation(element.getTexture());
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				RenderSystem.setShaderTexture(0, texture);

				graphics.blit(texture, x, y, element.getTextureX(), element.getTextureY(), element.getWidth(),
						element.getHeight(), element.getImageWidth(), element.getImageHeight());
			} catch (Exception e) {
				// Silent fail
			}
		}
	}

	private void renderItem(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y) {
		if (element.getItem() != null) {
			try {
				ResourceLocation itemId = new ResourceLocation(element.getItem());
				Item item = BuiltInRegistries.ITEM.get(itemId);
				ItemStack stack = new ItemStack(item);

				graphics.renderItem(stack, x, y);
			} catch (Exception e) {
				// Render barrier on error
				graphics.renderItem(new ItemStack(Items.BARRIER), x, y);
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (definition.getElements() != null) {
			for (GuiDefinition.GuiElement element : definition.getElements()) {
				if (element.getType().equals("button")) {
					int x = leftPos + element.getX();
					int y = topPos + element.getY();

					if (mouseX >= x && mouseX < x + element.getWidth() && mouseY >= y
							&& mouseY < y + element.getHeight()) {

						if (element.getScript() != null) {
							// Send packet to server to execute script
							Networking.sendToServer(new ButtonClickPacket(guiId, element.getId(), button));
						}

						return true;
					}
				}
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}