/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.gui;

import java.util.*;

import com.code.tama.tts.server.networking.Networking;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import com.code.tama.triggerapi.networking.gui.ButtonClickPacket;
import com.code.tama.triggerapi.networking.gui.CloseGuiPacket;
import com.code.tama.triggerapi.networking.gui.GuiStateUpdatePacket;

public class CustomGuiScreen extends Screen {
	private final GuiDefinition definition;
	public final ResourceLocation guiId;
	private int guiWidth; // Actual width
	private int guiHeight; // Actual height
	private int leftPos;
	private int topPos;
	private GuiDefinition.GuiElement hoveredElement = null;

	// Shared context from server (key-value pairs)
	private final Map<String, Object> sharedContext = new HashMap<>();

	// State tracking
	private final Map<String, Float> sliderValues = new HashMap<>();
	private final Map<String, Float> progressValues = new HashMap<>();
	private final Map<String, String> textBoxValues = new HashMap<>();
	private final Map<String, String> lastSentTextBoxValues = new HashMap<>(); // Track last sent values
	private final Map<String, Integer> dropdownSelections = new HashMap<>();
	private final Map<String, Boolean> switchStates = new HashMap<>();
	private final Map<String, Boolean> checkboxStates = new HashMap<>();
	private final Map<String, EditBox> editBoxes = new HashMap<>();
	private final Map<String, Boolean> dropdownOpen = new HashMap<>();
	private final Map<String, Entity> cachedEntities = new HashMap<>();

	private GuiDefinition.GuiElement draggedSlider = null;
	private long lastUpdateTime = 0;
	private static final long UPDATE_INTERVAL = 50; // Update every 50ms

	public CustomGuiScreen(GuiDefinition definition, ResourceLocation guiId) {
		super(Component.literal(definition.getTitle() != null ? definition.getTitle() : "GUI"));
		this.definition = definition;
		this.guiId = guiId;
		initializeStates();
	}

	private void initializeStates() {
		if (definition.getElements() != null) {
			for (GuiDefinition.GuiElement element : definition.getElements()) {
				switch (element.getType()) {
					case "slider" -> sliderValues.put(element.getId(), element.getDefaultValue());
					case "progress_bar" -> progressValues.put(element.getId(), 0.0f);
					case "text_box" -> {
						textBoxValues.put(element.getId(), "");
						lastSentTextBoxValues.put(element.getId(), "");
					}
					case "dropdown" -> dropdownSelections.put(element.getId(), element.getDefaultOption());
					case "switch" -> switchStates.put(element.getId(), element.isDefaultState());
					case "checkbox" -> checkboxStates.put(element.getId(), element.isDefaultState());
					case "entity" -> cacheEntity(element);
				}
			}
		}
	}

	/**
	 * Called by SyncContextPacket to update the shared context from server
	 */
	public void updateSharedContext(Map<String, Object> contextData) {
		sharedContext.putAll(contextData);
	}

	/**
	 * Get value from shared context (for Lua scripts)
	 */
	public Object getContextValue(String key) {
		return sharedContext.get(key);
	}

	/**
	 * Set value in shared context (for Lua scripts)
	 */
	public void setContextValue(String key, Object value) {
		sharedContext.put(key, value);
	}

	private void cacheEntity(GuiDefinition.GuiElement element) {
		if (element.getEntityType() == null)
			return;

		try {
			ResourceLocation entityId = new ResourceLocation(element.getEntityType());
			EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(entityId);

			if (entityType != null && minecraft != null && minecraft.level != null) {
				Entity entity = entityType.create(minecraft.level);

				if (entity != null && element.getEntityNbt() != null) {
					try {
						CompoundTag nbt = TagParser.parseTag(element.getEntityNbt());
						entity.load(nbt);
					} catch (Exception e) {
						// NBT parsing failed, use default entity
					}
				}

				cachedEntities.put(element.getId(), entity);
			}
		} catch (Exception e) {
			// Entity creation failed
		}
	}

	@Override
	protected void init() {
		super.init();

		if (definition.getFullscreen()) {
			this.guiWidth = this.width;
			this.guiHeight = this.height;
			this.leftPos = 0;
			this.topPos = 0;
		} else {
			this.guiWidth = definition.getWidth();
			this.guiHeight = definition.getHeight();
			// Center the GUI on screen
			this.leftPos = (this.width - this.guiWidth) / 2;
			this.topPos = (this.height - this.guiHeight) / 2;
		}
		// Initialize edit boxes
		editBoxes.clear();
		if (definition.getElements() != null) {
			for (GuiDefinition.GuiElement element : definition.getElements()) {
				if ("text_box".equals(element.getType())) {
					int x = leftPos + element.getX();
					int y = topPos + element.getY();

					EditBox editBox = new EditBox(this.font, x + 2, y + 2, element.getWidth() - 4,
							element.getHeight() - 4, Component.empty());
					editBox.setMaxLength(element.getMaxLength());
					editBox.setValue(textBoxValues.getOrDefault(element.getId(), ""));
					editBox.setHint(Component.literal(element.getHintText() != null ? element.getHintText() : ""));

					if (element.isMultiline()) {
						editBox.setMaxLength(element.getMaxLength() * 4);
					}

					editBoxes.put(element.getId(), editBox);
					this.addRenderableWidget(editBox);
				}
			}
		}
	}

	@Override
	public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(graphics);

		// Update text box values from EditBox widgets
		updateTextBoxValues();

		// Check for text box changes
		checkTextBoxChanges();

		// Update progress bars periodically
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastUpdateTime > UPDATE_INTERVAL) {
			updateProgressBars();
			lastUpdateTime = currentTime;
		}

		// Render background texture
		renderBackground(graphics);

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

	/**
	 * Sync text box values from EditBox widgets to our state map
	 */
	private void updateTextBoxValues() {
		for (Map.Entry<String, EditBox> entry : editBoxes.entrySet()) {
			textBoxValues.put(entry.getKey(), entry.getValue().getValue());
		}
	}

	/**
	 * Check if any text box values changed since last update and send packets
	 */
	private void checkTextBoxChanges() {
		for (Map.Entry<String, String> entry : textBoxValues.entrySet()) {
			String elementId = entry.getKey();
			String currentValue = entry.getValue();
			String lastSentValue = lastSentTextBoxValues.getOrDefault(elementId, "");

			// Only send if value changed
			if (!currentValue.equals(lastSentValue)) {
				lastSentTextBoxValues.put(elementId, currentValue);

				// Find the element definition
				if (definition.getElements() != null) {
					for (GuiDefinition.GuiElement element : definition.getElements()) {
						if (element.getId() != null && element.getId().equals(elementId)
								&& "text_box".equals(element.getType())) {
							if (element.getOnTextChangeScript() != null) {
								Networking.sendToServer(
										new GuiStateUpdatePacket(guiId, elementId, "text_change", currentValue));
							}
							break;
						}
					}
				}
			}
		}
	}

	public void renderBackground(@NotNull GuiGraphics graphics) {
		if (definition.getBackgroundTexture() != null) {
			try {
				ResourceLocation bgTexture = ResourceLocation.tryParse(definition.getBackgroundTexture());
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				assert bgTexture != null;
				RenderSystem.setShaderTexture(0, bgTexture);

				graphics.blit(bgTexture, leftPos, topPos, 0, 0, definition.getWidth(), definition.getHeight(),
						definition.getWidth(), definition.getHeight());
			} catch (Exception e) {
				graphics.fill(leftPos, topPos, leftPos + definition.getWidth(), topPos + definition.getHeight(),
						0xFF8B8B8B);
			}
		} else {
			graphics.fill(leftPos, topPos, leftPos + definition.getWidth(), topPos + definition.getHeight(),
					0xFF8B8B8B);
		}
	}

	private void updateProgressBars() {
		if (definition.getElements() == null)
			return;

		for (GuiDefinition.GuiElement element : definition.getElements()) {
			if ("progress_bar".equals(element.getType()) && element.getProgressScript() != null) {
				// Request progress value from server via script
				Networking.sendToServer(new GuiStateUpdatePacket(guiId, element.getId(), "request_progress", null));
			}
		}
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
			case "progress_bar" -> renderProgressBar(graphics, element, x, y);
			case "slider" -> renderSlider(graphics, element, x, y, hovered);
			case "entity" -> renderEntity(graphics, element, x, y);
			case "text_box" -> {
			} // Rendered by Minecraft's widget system
			case "dropdown" -> renderDropdown(graphics, element, x, y, hovered);
			case "switch" -> renderSwitch(graphics, element, x, y, hovered);
			case "checkbox" -> renderCheckbox(graphics, element, x, y, hovered);
		}
	}

	private void renderProgressBar(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y) {
		float progress = progressValues.getOrDefault(element.getId(), 0.0f);
		progress = Math.max(0.0f, Math.min(1.0f, progress));

		// Draw border
		graphics.fill(x - 1, y - 1, x + element.getWidth() + 1, y + element.getHeight() + 1, element.getBorderColor());

		// Draw background
		graphics.fill(x, y, x + element.getWidth(), y + element.getHeight(), element.getBackgroundColor());

		// Draw progress
		if (element.isVertical()) {
			int fillHeight = (int) (element.getHeight() * progress);
			graphics.fill(x, y + element.getHeight() - fillHeight, x + element.getWidth(), y + element.getHeight(),
					element.getBarColor());
		} else {
			int fillWidth = (int) (element.getWidth() * progress);
			graphics.fill(x, y, x + fillWidth, y + element.getHeight(), element.getBarColor());
		}

		// Draw percentage text
		if (element.shouldShowPercentage()) {
			String text = String.format("%.0f%%", progress * 100);
			int textX = x + (element.getWidth() - font.width(text)) / 2;
			int textY = y + (element.getHeight() - font.lineHeight) / 2;
			graphics.drawString(font, text, textX, textY, 0xFFFFFFFF, true);
		}
	}

	private void renderSlider(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y, boolean hovered) {
		float value = sliderValues.getOrDefault(element.getId(), element.getDefaultValue());
		float normalized = (value - element.getMinValue()) / (element.getMaxValue() - element.getMinValue());
		normalized = Math.max(0.0f, Math.min(1.0f, normalized));

		// Draw slider track
		int trackY = y + element.getHeight() / 2 - 2;
		graphics.fill(x, trackY, x + element.getWidth(), trackY + 4, element.getSliderColor());

		// Draw handle
		int handleX = x + (int) (normalized * element.getWidth()) - 4;
		int handleColor = hovered || draggedSlider == element ? 0xFFFFFFFF : element.getHandleColor();
		graphics.fill(handleX, y, handleX + 8, y + element.getHeight(), handleColor);

		// Draw value
		if (element.shouldShowValue()) {
			String valueText = String.format("%.1f", value);
			int textX = x + element.getWidth() + 5;
			int textY = y + (element.getHeight() - font.lineHeight) / 2;
			graphics.drawString(font, valueText, textX, textY, element.getTextColor(), false);
		}
	}

	private void renderEntity(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y) {
		Entity entity = cachedEntities.get(element.getId());
		if (entity instanceof LivingEntity living) {
			int centerX = x + element.getWidth() / 2;
			int centerY = y + element.getHeight();

			renderEntityInInventory(graphics, centerX, centerY, element.getEntityScale(), element.shouldRotateEntity(),
					living);
		}
	}

	private void renderEntityInInventory(GuiGraphics graphics, int x, int y, float scale, boolean rotate,
			LivingEntity entity) {
		PoseStack poseStack = graphics.pose();
		poseStack.pushPose();
		poseStack.translate(x, y, 50.0);
		poseStack.scale(scale, scale, scale);
		poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));

		if (rotate) {
			float rotation = (System.currentTimeMillis() % 3600) / 10.0f;
			poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
		}

		EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
		dispatcher.setRenderShadow(false);
		dispatcher.render(entity, 0, 0, 0, 0.0F, 1.0F, poseStack, graphics.bufferSource(), 15728880);
		dispatcher.setRenderShadow(true);

		poseStack.popPose();
		graphics.flush();
	}

	private void renderDropdown(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y, boolean hovered) {
		int selectedIndex = dropdownSelections.getOrDefault(element.getId(), 0);
		boolean isOpen = dropdownOpen.getOrDefault(element.getId(), false);

		// Draw main box
		int bgColor = hovered ? 0xFFCCCCCC : element.getDropdownColor();
		graphics.fill(x, y, x + element.getWidth(), y + element.getHeight(), bgColor);
		graphics.fill(x, y, x + element.getWidth(), y + 1, 0xFF000000);
		graphics.fill(x, y, x + 1, y + element.getHeight(), 0xFF000000);
		graphics.fill(x + element.getWidth() - 1, y, x + element.getWidth(), y + element.getHeight(), 0xFF000000);
		graphics.fill(x, y + element.getHeight() - 1, x + element.getWidth(), y + element.getHeight(), 0xFF000000);

		// Draw selected option
		if (element.getOptions() != null && selectedIndex < element.getOptions().size()) {
			String selected = element.getOptions().get(selectedIndex);
			graphics.drawString(font, selected, x + 4, y + (element.getHeight() - font.lineHeight) / 2,
					element.getTextColor(), false);
		}

		// Draw arrow
		graphics.drawString(font, isOpen ? "▲" : "▼", x + element.getWidth() - 12,
				y + (element.getHeight() - font.lineHeight) / 2, 0xFF000000, false);

		// Draw dropdown options if open
		if (isOpen && element.getOptions() != null) {
			int optionY = y + element.getHeight();
			for (int i = 0; i < element.getOptions().size(); i++) {
				String option = element.getOptions().get(i);
				int optionBg = i == selectedIndex ? element.getSelectedColor() : 0xFFFFFFFF;
				graphics.fill(x, optionY, x + element.getWidth(), optionY + element.getHeight(), optionBg);
				graphics.drawString(font, option, x + 4, optionY + 2, element.getTextColor(), false);
				optionY += element.getHeight();
			}
		}
	}

	private void renderSwitch(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y, boolean hovered) {
		boolean state = switchStates.getOrDefault(element.getId(), false);

		// Draw track
		int trackColor = state ? element.getOnColor() : element.getOffColor();
		int trackHeight = element.getHeight();
		graphics.fill(x, y, x + element.getWidth(), y + trackHeight, trackColor);

		// Draw handle
		int handleWidth = element.getWidth() / 2 - 2;
		int handleX = state ? x + element.getWidth() / 2 + 2 : x + 2;
		int handleColor = hovered ? 0xFFFFFFFF : 0xFFCCCCCC;
		graphics.fill(handleX, y + 2, handleX + handleWidth, y + trackHeight - 2, handleColor);
	}

	private void renderCheckbox(GuiGraphics graphics, GuiDefinition.GuiElement element, int x, int y, boolean hovered) {
		boolean checked = checkboxStates.getOrDefault(element.getId(), false);

		// Draw box
		int boxSize = Math.min(element.getWidth(), element.getHeight());
		int bgColor = hovered ? 0xFFCCCCCC : 0xFFFFFFFF;
		graphics.fill(x, y, x + boxSize, y + boxSize, bgColor);
		graphics.fill(x, y, x + boxSize, y + 1, 0xFF000000);
		graphics.fill(x, y, x + 1, y + boxSize, 0xFF000000);
		graphics.fill(x + boxSize - 1, y, x + boxSize, y + boxSize, 0xFF000000);
		graphics.fill(x, y + boxSize - 1, x + boxSize, y + boxSize, 0xFF000000);

		// Draw checkmark
		if (checked) {
			if (element.getCheckedTexture() != null) {
				try {
					ResourceLocation texture = new ResourceLocation(element.getCheckedTexture());
					graphics.blit(texture, x + 2, y + 2, 0, 0, boxSize - 4, boxSize - 4, boxSize - 4, boxSize - 4);
				} catch (Exception e) {
					// Draw simple checkmark
					graphics.drawString(font, "✓", x + 2, y + 2, element.getCheckColor(), false);
				}
			} else {
				graphics.drawString(font, "✓", x + 2, y + 2, element.getCheckColor(), false);
			}
		}

		// Draw label
		if (element.getLabel() != null) {
			graphics.drawString(font, element.getLabel(), x + boxSize + 4, y + (boxSize - font.lineHeight) / 2,
					element.getLabelColor(), false);
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
				int color = hovered ? 0xFFFFFFFF : 0xFFAAAAAA;
				graphics.fill(x, y, x + element.getWidth(), y + element.getHeight(), color);
			}
		} else {
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
				graphics.renderItem(new ItemStack(Items.BARRIER), x, y);
			}
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (definition.getElements() != null) {
			for (GuiDefinition.GuiElement element : definition.getElements()) {
				int x = leftPos + element.getX();
				int y = topPos + element.getY();

				if (mouseX >= x && mouseX < x + element.getWidth() && mouseY >= y && mouseY < y + element.getHeight()) {

					switch (element.getType()) {
						case "button" -> {
							if (element.getScript() != null) {
								Networking.sendToServer(new ButtonClickPacket(guiId, element.getId(), button));
							}
							return true;
						}
						case "slider" -> {
							draggedSlider = element;
							updateSliderValue(element, mouseX);
							return true;
						}
						case "dropdown" -> {
							handleDropdownClick(element, mouseX, mouseY);
							return true;
						}
						case "switch" -> {
							toggleSwitch(element);
							return true;
						}
						case "checkbox" -> {
							toggleCheckbox(element);
							return true;
						}
					}
				}
			}
		}

		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (draggedSlider != null) {
			updateSliderValue(draggedSlider, mouseX);
			return true;
		}
		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (draggedSlider != null) {
			draggedSlider = null;
			return true;
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}

	private void updateSliderValue(GuiDefinition.GuiElement element, double mouseX) {
		int x = leftPos + element.getX();
		float normalized = (float) ((mouseX - x) / element.getWidth());
		normalized = Math.max(0.0f, Math.min(1.0f, normalized));

		float value = element.getMinValue() + normalized * (element.getMaxValue() - element.getMinValue());

		// Apply step
		if (element.getStep() > 0) {
			value = Math.round(value / element.getStep()) * element.getStep();
		}

		sliderValues.put(element.getId(), value);

		if (element.getOnChangeScript() != null) {
			Networking.sendToServer(
					new GuiStateUpdatePacket(guiId, element.getId(), "slider_change", String.valueOf(value)));
		}
	}

	private void handleDropdownClick(GuiDefinition.GuiElement element, double mouseX, double mouseY) {
		boolean isOpen = dropdownOpen.getOrDefault(element.getId(), false);
		int x = leftPos + element.getX();
		int y = topPos + element.getY();

		if (!isOpen) {
			dropdownOpen.put(element.getId(), true);
		} else {
			// Check if clicking on an option
			int selectedIndex = dropdownSelections.getOrDefault(element.getId(), 0);
			int optionY = y + element.getHeight();

			if (element.getOptions() != null) {
				for (int i = 0; i < element.getOptions().size(); i++) {
					if (mouseY >= optionY && mouseY < optionY + element.getHeight()) {
						dropdownSelections.put(element.getId(), i);

						if (element.getOnSelectScript() != null) {
							Networking.sendToServer(new GuiStateUpdatePacket(guiId, element.getId(), "dropdown_select",
									String.valueOf(i)));
						}
						break;
					}
					optionY += element.getHeight();
				}
			}

			dropdownOpen.put(element.getId(), false);
		}
	}

	private void toggleSwitch(GuiDefinition.GuiElement element) {
		boolean newState = !switchStates.getOrDefault(element.getId(), false);
		switchStates.put(element.getId(), newState);

		if (element.getOnToggleScript() != null) {
			Networking.sendToServer(
					new GuiStateUpdatePacket(guiId, element.getId(), "switch_toggle", String.valueOf(newState)));
		}
	}

	private void toggleCheckbox(GuiDefinition.GuiElement element) {
		boolean newState = !checkboxStates.getOrDefault(element.getId(), false);
		checkboxStates.put(element.getId(), newState);

		if (element.getOnToggleScript() != null) {
			Networking.sendToServer(
					new GuiStateUpdatePacket(guiId, element.getId(), "checkbox_toggle", String.valueOf(newState)));
		}
	}

	public void updateProgress(String elementId, float value) {
		progressValues.put(elementId, value);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	@Override
	public void onClose() {
		Networking.sendToServer(new CloseGuiPacket(this.guiId));
		super.onClose();
	}
}