/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

/**
 * Top-down map of the ARS grid. Shows every placed room at its actual grid
 * position, supports panning (click-drag) and zooming (scroll wheel), lets you
 * page up/down through Y layers, and clicking a cell opens the room picker for
 * that cell.
 */
public class ARSMapScreen extends Screen {

	/** The very first ARS room always sits here - this is where the view opens. */
	private static final BlockPos DEFAULT_FOCUS = new BlockPos(0, 128, 0);

	private static final double MIN_CELL_PIXELS = 12.0;
	private static final double MAX_CELL_PIXELS = 96.0;
	private static final double DRAG_THRESHOLD = 4.0;

	@Nullable private final Level level;
	private List<ARSGrid> grids;

	// Camera: panX/panZ are grid-space coordinates (not blocks) at the center of
	// the screen.
	private double panX;
	private double panZ;
	private int currentLayer;
	private double cellPixels = 32.0;

	// Drag tracking
	private boolean dragging = false;
	private double dragStartMouseX, dragStartMouseY;
	private double dragStartPanX, dragStartPanZ;

	@Nullable private ARSGrid hoveredGrid;

	public ARSMapScreen(List<ARSGrid> grids) {
		this(null, grids);
	}

	public ARSMapScreen(@Nullable Level level, List<ARSGrid> grids) {
		super(Component.literal("ARS Map"));
		this.level = level;
		this.grids = grids;
	}

	/** pulls the current grid list straight off the level capability. */
	public static ARSMapScreen fromLevel(Level level) {
		List<ARSGrid> grids = TARDISLevelCapability.GetTARDISCapSupplier(level).map(ITARDISLevel::getARSGrids)
				.orElse(new ArrayList<>());
		return new ARSMapScreen(level, grids);
	}

	/**
	 * Re-pulls the grid list from the capability, if this screen was given a level.
	 */
	public void refreshGrids() {
		if (level != null) {
			this.grids = TARDISLevelCapability.GetTARDISCapSupplier(level).map(ITARDISLevel::getARSGrids)
					.orElse(this.grids);
		}
	}

	/**
	 * Use this if you're managing the list yourself (e.g. passed a static list in).
	 */
	public void setGrids(List<ARSGrid> grids) {
		this.grids = grids;
	}

	@Override
	protected void init() {
		ARSPos focus = ARSPos.fromBlockPos(DEFAULT_FOCUS);
		this.panX = focus.getX() + 0.5f;
		this.panZ = focus.getZ() + 0.5f;
		this.currentLayer = focus.getY();

		int buttonY = this.height - 28;

		this.addRenderableWidget(
				Button.builder(Component.literal("Layer -"), b -> changeLayer(-1)).bounds(10, buttonY, 60, 20).build());
		this.addRenderableWidget(
				Button.builder(Component.literal("Layer +"), b -> changeLayer(1)).bounds(75, buttonY, 60, 20).build());
		this.addRenderableWidget(
				Button.builder(Component.literal("Recenter"), b -> recenter()).bounds(145, buttonY, 70, 20).build());
		this.addRenderableWidget(Button.builder(Component.literal("Close"), b -> onClose())
				.bounds(this.width - 70, buttonY, 60, 20).build());
	}

	private void recenter() {
		ARSPos focus = ARSPos.fromBlockPos(DEFAULT_FOCUS);
		this.panX = focus.getX();
		this.panZ = focus.getZ();
		this.currentLayer = focus.getY();
		this.cellPixels = 32.0;
	}

	private void changeLayer(int delta) {
		this.currentLayer += delta;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		if (this.minecraft == null)
			return;
		this.renderBackground(guiGraphics);

		drawGridLines(guiGraphics);
		this.hoveredGrid = drawRooms(guiGraphics, mouseX, mouseY);

		super.render(guiGraphics, mouseX, mouseY, partialTick);

		drawHeader(guiGraphics);

		if (hoveredGrid != null) {
			guiGraphics.renderTooltip(this.font, Component.literal(hoveredGrid.getName()), mouseX, mouseY);
		}
	}

	private void drawHeader(GuiGraphics guiGraphics) {
		int layerLow = currentLayer * ARSPos.CELL_SIZE;
		int layerHigh = layerLow + ARSPos.CELL_SIZE - 1;
		Component text = Component
				.literal(String.format("Layer %d  (Y %d-%d)   |   drag to pan, scroll to zoom, click a cell to edit",
						currentLayer, layerLow, layerHigh));
		guiGraphics.drawString(this.font, text, 10, 10, 0xFFFFFF);
	}

	/** Converts a grid-space x/z coordinate to a screen pixel position. */
	private double gridToScreenX(double gridX) {
		return this.width / 2.0 + (gridX - panX) * cellPixels;
	}

	private double gridToScreenZ(double gridZ) {
		return this.height / 2.0 + (gridZ - panZ) * cellPixels;
	}

	private double screenToGridX(double screenX) {
		return panX + (screenX - this.width / 2.0) / cellPixels;
	}

	private double screenToGridZ(double screenZ) {
		return panZ + (screenZ - this.height / 2.0) / cellPixels;
	}

	private void drawGridLines(GuiGraphics guiGraphics) {
		int color = 0x33FFFFFF;

		double minGridX = screenToGridX(0);
		double maxGridX = screenToGridX(this.width);
		double minGridZ = screenToGridZ(0);
		double maxGridZ = screenToGridZ(this.height);

		int startX = (int) Math.floor(minGridX);
		int endX = (int) Math.ceil(maxGridX);
		int startZ = (int) Math.floor(minGridZ);
		int endZ = (int) Math.ceil(maxGridZ);

		for (int gx = startX; gx <= endX; gx++) {
			int sx = (int) Math.round(gridToScreenX(gx));
			guiGraphics.fill(sx, 0, sx + 1, this.height, color);
		}
		for (int gz = startZ; gz <= endZ; gz++) {
			int sz = (int) Math.round(gridToScreenZ(gz));
			guiGraphics.fill(0, sz, this.width, sz + 1, color);
		}
	}

	/**
	 * Draws every room on the current layer, returns whichever one the mouse is
	 * over (if any).
	 */
	@Nullable private ARSGrid drawRooms(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		ARSGrid hovered = null;

		for (ARSGrid grid : grids) {
			ARSPos pos = grid.getPos();
			if (pos.getY() != currentLayer)
				continue;

			int x0 = (int) Math.round(gridToScreenX(pos.getX()));
			int z0 = (int) Math.round(gridToScreenZ(pos.getZ()));
			int x1 = (int) Math.round(gridToScreenX(pos.getX() + 1));
			int z1 = (int) Math.round(gridToScreenZ(pos.getZ() + 1));

			if (x1 < 0 || z1 < 0 || x0 > this.width || z0 > this.height)
				continue; // off-screen

			boolean isHovered = mouseX >= x0 && mouseX < x1 && mouseY >= z0 && mouseY < z1;
			int fillColor = (isHovered ? 0xFF000000 : 0xCC000000) | grid.getColor();

			guiGraphics.fill(x0, z0, x1, z1, fillColor);
			guiGraphics.fill(x0, z0, x1, z0 + 1, 0xFFFFFFFF); // top border
			guiGraphics.fill(x0, z0, x0 + 1, z1, 0xFFFFFFFF); // left border
			guiGraphics.fill(x0, z1 - 1, x1, z1, 0xFFFFFFFF); // bottom border
			guiGraphics.fill(x1 - 1, z0, x1, z1, 0xFFFFFFFF); // right border

			if (isHovered)
				hovered = grid;
		}

		return hovered;
	}

	@Nullable private ARSGrid getGridAt(ARSPos pos) {
		for (ARSGrid grid : grids) {
			if (grid.getPos().equals(pos))
				return grid;
		}
		return null;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (button == 0) {
			dragging = false;
			dragStartMouseX = mouseX;
			dragStartMouseY = mouseY;
			dragStartPanX = panX;
			dragStartPanZ = panZ;
		}
		// Let widgets (the buttons) get first crack at the click.
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (super.mouseDragged(mouseX, mouseY, button, dragX, dragY)) {
			return true; // a widget consumed it
		}
		if (button == 0) {
			double totalDelta = Math.hypot(mouseX - dragStartMouseX, mouseY - dragStartMouseY);
			if (totalDelta > DRAG_THRESHOLD) {
				dragging = true;
				panX = dragStartPanX - (mouseX - dragStartMouseX) / cellPixels;
				panZ = dragStartPanZ - (mouseY - dragStartMouseY) / cellPixels;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (button == 0 && !dragging) {
			// A plain click (no drag) on a grid cell -> open the room picker for that cell.
			int cellX = (int) Math.floor(screenToGridX(mouseX));
			int cellZ = (int) Math.floor(screenToGridZ(mouseY));
			ARSPos clicked = new ARSPos(cellX, currentLayer, cellZ);

			// Ignore clicks that landed on a button/widget rather than empty map space.
			if (!isOverAnyWidget(mouseX, mouseY)) {
				openRoomPicker(clicked);
			}
		}
		dragging = false;
		return super.mouseReleased(mouseX, mouseY, button);
	}

	private boolean isOverAnyWidget(double mouseX, double mouseY) {
		for (var widget : this.renderables) {
			if (widget instanceof net.minecraft.client.gui.components.AbstractWidget aw
					&& aw.isMouseOver(mouseX, mouseY)) {
				return true;
			}
		}
		return false;
	}

	private void openRoomPicker(ARSPos pos) {
		ARSGrid existing = getGridAt(pos);
		this.minecraft.setScreen(new ARSRoomSelectScreen(this, pos, existing));
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		if (super.mouseScrolled(mouseX, mouseY, delta))
			return true;

		// Zoom, keeping the point currently under the cursor fixed on screen.
		double gridXBefore = screenToGridX(mouseX);
		double gridZBefore = screenToGridZ(mouseY);

		double factor = Math.pow(1.15, delta);
		cellPixels = Math.max(MIN_CELL_PIXELS, Math.min(MAX_CELL_PIXELS, cellPixels * factor));

		double gridXAfter = screenToGridX(mouseX);
		double gridZAfter = screenToGridZ(mouseY);
		panX += gridXBefore - gridXAfter;
		panZ += gridZBefore - gridZAfter;

		return true;
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_PAGE_UP) {
			changeLayer(1);
			return true;
		}
		if (keyCode == GLFW.GLFW_KEY_PAGE_DOWN) {
			changeLayer(-1);
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}