/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.gui;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * Represents a custom GUI definition loaded from JSON Location:
 * data/modid/triggerapi/gui/example.json
 */
@Getter
public class GuiDefinition {

	// Getters
	@SerializedName("type")
	private String type = "custom"; // "custom" or "container"

	@SerializedName("title")
	private String title;

	// Container-specific
	@SerializedName("size")
	private int size = 27;

	@SerializedName("container_buttons")
	private List<ContainerButtonDefinition> containerButtons;

	@SerializedName("background")
	private BackgroundDefinition background;

	// Custom GUI specific
	@SerializedName("width")
	private int width = 176;

	@SerializedName("height")
	private int height = 166;

	@SerializedName("background_texture")
	private String backgroundTexture;

	@SerializedName("elements")
	private List<GuiElement> elements;

	// Container button (for chest-like GUIs)
	@Getter
	public static class ContainerButtonDefinition {
		@SerializedName("slot")
		private int slot;

		@SerializedName("item")
		private String item;

		@SerializedName("name")
		private String name;

		@SerializedName("lore")
		private List<String> lore;

		@SerializedName("glow")
		private boolean glow = false;

		@SerializedName("script")
		private String script;

		@SerializedName("close_on_click")
		private boolean closeOnClick = false;

		public boolean shouldCloseOnClick() {
			return closeOnClick;
		}
	}

	public static class BackgroundDefinition {
		@Getter
		@SerializedName("item")
		private String item;

		@SerializedName("fill_empty")
		private boolean fillEmpty = false;

		public boolean shouldFillEmpty() {
			return fillEmpty;
		}
	}

	// Custom GUI element (button, text, image, etc.)
	public static class GuiElement {
		// Getters
		@Getter
		@SerializedName("type")
		private String type; // "button", "text", "image", "item"

		@Getter
		@SerializedName("id")
		private String id;

		@Getter
		@SerializedName("x")
		private int x;

		@Getter
		@SerializedName("y")
		private int y;

		@Getter
		@SerializedName("width")
		private int width;

		@Getter
		@SerializedName("height")
		private int height;

		// Button specific
		@Getter
		@SerializedName("texture")
		private String texture;

		@Getter
		@SerializedName("texture_x")
		private int textureX = 0;

		@Getter
		@SerializedName("texture_y")
		private int textureY = 0;

		@Getter
		@SerializedName("hover_texture_y")
		private int hoverTextureY = 0;

		@Getter
		@SerializedName("script")
		private String script;

		// Text specific
		@Getter
		@SerializedName("text")
		private String text;

		@Getter
		@SerializedName("color")
		private int color = 0x404040;

		@SerializedName("shadow")
		private boolean shadow = false;

		@Getter
		@SerializedName("scale")
		private float scale = 1.0f;

		// Item specific
		@Getter
		@SerializedName("item")
		private String item;

		@Getter
		@SerializedName("tooltip")
		private List<String> tooltip;

		// Image specific
		@Getter
		@SerializedName("image_width")
		private int imageWidth = 256;

		@Getter
		@SerializedName("image_height")
		private int imageHeight = 256;

		public boolean hasShadow() {
			return shadow;
		}
	}
}