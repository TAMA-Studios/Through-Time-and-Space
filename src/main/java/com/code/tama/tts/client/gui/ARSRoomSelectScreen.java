/* (C) TAMA Studios 2026 */
package com.code.tama.tts.client.gui;

import javax.annotation.Nullable;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Scrollable list of placeable ARS rooms. Selecting one and pressing "Place"
 * calls ARSRoomRegistry.placeRoom(pos, roomId) then returns to the map.
 */
public class ARSRoomSelectScreen extends Screen {

	private final ARSMapScreen parent;
	private final ARSPos targetPos;
	@Nullable private final ARSGrid existingRoom; // non-null if this cell is already occupied

	private RoomList roomList;
	private Button placeButton;

	public ARSRoomSelectScreen(ARSMapScreen parent, ARSPos targetPos, @Nullable ARSGrid existingRoom) {
		super(Component.literal("Select ARS Room"));
		this.parent = parent;
		this.targetPos = targetPos;
		this.existingRoom = existingRoom;
	}

	@Override
	protected void init() {
		int listTop = 40;
		int listBottom = this.height - 40;
		this.roomList = new RoomList(this.minecraft, this.width, listBottom - listTop, listTop, listBottom, 24);
		this.addWidget(this.roomList);

		for (ARSRoomRegistry.ARSRoomInfo info : ARSRoomRegistry.getAvailableRooms()) {
			this.roomList.children().add(this.roomList.new RoomEntry(info));
		}

		this.placeButton = Button.builder(Component.literal("Place"), b -> confirmPlacement())
				.bounds(this.width / 2 - 105, this.height - 30, 100, 20).build();
		this.placeButton.active = false;
		this.addRenderableWidget(this.placeButton);

		this.addRenderableWidget(Button.builder(Component.literal("Cancel"), b -> onClose())
				.bounds(this.width / 2 + 5, this.height - 30, 100, 20).build());
	}

	private void confirmPlacement() {
		RoomList.RoomEntry selected = this.roomList.getSelected();
		if (selected == null)
			return;

		ARSRoomRegistry.placeRoom(targetPos, selected.info);

		parent.refreshGrids();
		this.minecraft.setScreen(parent);
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(guiGraphics);
		this.roomList.render(guiGraphics, mouseX, mouseY, partialTick);
		super.render(guiGraphics, mouseX, mouseY, partialTick);

		String header = existingRoom != null
				? "Replace room at " + targetPos + " (currently: " + existingRoom.getName() + ")"
				: "Place a room at " + targetPos;
		guiGraphics.drawCenteredString(this.font, header, this.width / 2, 15, 0xFFFFFF);
	}

	@Override
	public void onClose() {
		this.minecraft.setScreen(parent);
	}

	/** The scrollable room list. */
	private class RoomList extends ObjectSelectionList<RoomList.RoomEntry> {

		RoomList(net.minecraft.client.Minecraft minecraft, int width, int height, int y0, int y1, int itemHeight) {
			super(minecraft, width, height, y0, y1, itemHeight);
		}

		@Override
		public void setSelected(@Nullable RoomEntry entry) {
			super.setSelected(entry);
			placeButton.active = entry != null;
		}

		@Override
		public int getRowWidth() {
			return this.width - 20;
		}

		@Override
		protected int getScrollbarPosition() {
			return this.getRowLeft() + this.getRowWidth() + 4;
		}

		class RoomEntry extends ObjectSelectionList.Entry<RoomEntry> {
			final ARSRoomRegistry.ARSRoomInfo info;

			RoomEntry(ARSRoomRegistry.ARSRoomInfo info) {
				this.info = info;
			}

			@Override
			public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX,
					int mouseY, boolean hovered, float partialTick) {
				int swatchSize = height - 8;
				guiGraphics.fill(left + 4, top + 4, left + 4 + swatchSize, top + 4 + swatchSize,
						0xFF000000 | info.color());
				guiGraphics.drawString(RoomList.this.minecraft.font, info.displayName(), left + swatchSize + 12,
						top + height / 2 - 4, 0xFFFFFF);
			}

			@Override
			public boolean mouseClicked(double mouseX, double mouseY, int button) {
				RoomList.this.setSelected(this);
				return true;
			}

			@Override
			public Component getNarration() {
				return Component.literal(info.displayName());
			}
		}
	}
}