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
	int width = 176;

	@SerializedName("height")
	int height = 166;

	@SerializedName("fullscreen")
	private Boolean fullscreen = false;

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
		private String type; // "button", "text", "image", "item", "progress_bar", "slider", "entity",
								// "text_box", "dropdown", "switch", "checkbox"

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

		// Progress bar specific
		@Getter
		@SerializedName("progress_script")
		private String progressScript; // Lua function that returns 0.0-1.0

		@Getter
		@SerializedName("bar_color")
		private int barColor = 0xFF00FF00; // Green by default

		@Getter
		@SerializedName("background_color")
		private int backgroundColor = 0xFF808080; // Gray by default

		@Getter
		@SerializedName("border_color")
		private int borderColor = 0xFF000000; // Black by default

		@SerializedName("show_percentage")
		private boolean showPercentage = false;

		@Getter
		@SerializedName("vertical")
		private boolean vertical = false;

		// Slider specific
		@Getter
		@SerializedName("min_value")
		private float minValue = 0.0f;

		@Getter
		@SerializedName("max_value")
		private float maxValue = 100.0f;

		@Getter
		@SerializedName("default_value")
		private float defaultValue = 50.0f;

		@Getter
		@SerializedName("step")
		private float step = 1.0f;

		@Getter
		@SerializedName("slider_color")
		private int sliderColor = 0xFFFFFFFF; // White by default

		@Getter
		@SerializedName("handle_color")
		private int handleColor = 0xFF8B8B8B;

		@SerializedName("show_value")
		private boolean showValue = true;

		@Getter
		@SerializedName("on_change_script")
		private String onChangeScript;

		// Entity rendering specific
		@Getter
		@SerializedName("entity_type")
		private String entityType; // e.g., "minecraft:zombie"

		@Getter
		@SerializedName("entity_scale")
		private float entityScale = 30.0f;

		@Getter
		@SerializedName("rotate_entity")
		private boolean rotateEntity = true;

		@Getter
		@SerializedName("entity_nbt")
		private String entityNbt; // Optional NBT data

		// Text box specific
		@Getter
		@SerializedName("max_length")
		private int maxLength = 32;

		@Getter
		@SerializedName("hint_text")
		private String hintText;

		@Getter
		@SerializedName("text_color")
		private int textColor = 0xFFFFFFFF;

		@Getter
		@SerializedName("hint_color")
		private int hintColor = 0xFF808080;

		@Getter
		@SerializedName("multiline")
		private boolean multiline = false;

		@Getter
		@SerializedName("on_text_change_script")
		private String onTextChangeScript;

		// Dropdown specific
		@Getter
		@SerializedName("options")
		private List<String> options;

		@Getter
		@SerializedName("default_option")
		private int defaultOption = 0;

		@Getter
		@SerializedName("dropdown_color")
		private int dropdownColor = 0xFFFFFFFF;

		@Getter
		@SerializedName("selected_color")
		private int selectedColor = 0xFF00FF00;

		@Getter
		@SerializedName("on_select_script")
		private String onSelectScript;

		// Switch specific
		@Getter
		@SerializedName("default_state")
		private boolean defaultState = false;

		@Getter
		@SerializedName("on_color")
		private int onColor = 0xFF00FF00;

		@Getter
		@SerializedName("off_color")
		private int offColor = 0xFFFF0000;

		@Getter
		@SerializedName("on_toggle_script")
		private String onToggleScript;

		// Checkbox specific (similar to switch but different appearance)
		@Getter
		@SerializedName("checked_texture")
		private String checkedTexture;

		@Getter
		@SerializedName("unchecked_texture")
		private String uncheckedTexture;

		@Getter
		@SerializedName("check_color")
		private int checkColor = 0xFF00FF00;

		@Getter
		@SerializedName("label")
		private String label;

		@Getter
		@SerializedName("label_color")
		private int labelColor = 0xFF404040;

		public boolean hasShadow() {
			return shadow;
		}

		public boolean shouldShowPercentage() {
			return showPercentage;
		}

		public boolean shouldShowValue() {
			return showValue;
		}

		public boolean shouldRotateEntity() {
			return rotateEntity;
		}

	}
}