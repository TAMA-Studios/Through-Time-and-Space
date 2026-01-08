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

		public int getSlot() {
			return slot;
		}
		public String getItem() {
			return item;
		}
		public String getName() {
			return name;
		}
		public List<String> getLore() {
			return lore;
		}
		public boolean isGlow() {
			return glow;
		}
		public String getScript() {
			return script;
		}
		public boolean shouldCloseOnClick() {
			return closeOnClick;
		}
	}

	public static class BackgroundDefinition {
		@SerializedName("item")
		private String item;

		@SerializedName("fill_empty")
		private boolean fillEmpty = false;

		public String getItem() {
			return item;
		}
		public boolean shouldFillEmpty() {
			return fillEmpty;
		}
	}

	// Custom GUI element (button, text, image, etc.)
	public static class GuiElement {
		@SerializedName("type")
		private String type; // "button", "text", "image", "item"

		@SerializedName("id")
		private String id;

		@SerializedName("x")
		private int x;

		@SerializedName("y")
		private int y;

		@SerializedName("width")
		private int width;

		@SerializedName("height")
		private int height;

		// Button specific
		@SerializedName("texture")
		private String texture;

		@SerializedName("texture_x")
		private int textureX = 0;

		@SerializedName("texture_y")
		private int textureY = 0;

		@SerializedName("hover_texture_y")
		private int hoverTextureY = 0;

		@SerializedName("script")
		private String script;

		// Text specific
		@SerializedName("text")
		private String text;

		@SerializedName("color")
		private int color = 0x404040;

		@SerializedName("shadow")
		private boolean shadow = false;

		@SerializedName("scale")
		private float scale = 1.0f;

		// Item specific
		@SerializedName("item")
		private String item;

		@SerializedName("tooltip")
		private List<String> tooltip;

		// Image specific
		@SerializedName("image_width")
		private int imageWidth = 256;

		@SerializedName("image_height")
		private int imageHeight = 256;

		// Getters
		public String getType() {
			return type;
		}
		public String getId() {
			return id;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public int getWidth() {
			return width;
		}
		public int getHeight() {
			return height;
		}
		public String getTexture() {
			return texture;
		}
		public int getTextureX() {
			return textureX;
		}
		public int getTextureY() {
			return textureY;
		}
		public int getHoverTextureY() {
			return hoverTextureY;
		}
		public String getScript() {
			return script;
		}
		public String getText() {
			return text;
		}
		public int getColor() {
			return color;
		}
		public boolean hasShadow() {
			return shadow;
		}
		public float getScale() {
			return scale;
		}
		public String getItem() {
			return item;
		}
		public List<String> getTooltip() {
			return tooltip;
		}
		public int getImageWidth() {
			return imageWidth;
		}
		public int getImageHeight() {
			return imageHeight;
		}
	}
}