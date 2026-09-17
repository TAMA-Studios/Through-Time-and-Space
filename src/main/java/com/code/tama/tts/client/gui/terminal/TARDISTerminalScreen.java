/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.gui.terminal;

import java.util.List;

import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.C2S.dimensions.TerminalActionPacketC2S;
import com.code.tama.tts.core.networking.packets.C2S.dimensions.TerminalActionPacketC2S.Action;
import com.code.tama.tts.core.networking.packets.C2S.dimensions.TerminalSendMessagePacketC2S;
import com.code.tama.tts.core.networking.packets.C2S.dimensions.TerminalSetDestinationPacketC2S;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * A retro CRT-style computer terminal for controlling every aspect of the
 * TARDIS: flight, navigation, power/systems, doors, and inter-TARDIS comms.
 *
 * <p>
 * This screen holds a live reference to the client-side {@link ITARDISLevel}
 * capability. Because we already keep that in sync with the server, all this
 * screen has to do is read the capability's current fields every frame, and
 * fire C2S packets when a button is pressed. The next capability sync packet
 * from the server then updates the same object in place, so the screen "just
 * werks" without any extra client-side state.
 * </p>
 */
public class TARDISTerminalScreen extends Screen {

	// ---- CRT theme ----
	private static final int COL_BG = 0xE0020A02;
	private static final int COL_FRAME = 0xFF0F3A16;
	private static final int COL_FRAME_LIGHT = 0xFF1F6B2A;
	private static final int COL_TEXT = 0xFF3CFF5E;
	private static final int COL_TEXT_DIM = 0xFF1E8A34;
	private static final int COL_TEXT_WARN = 0xFFFFC94A;
	private static final int COL_TEXT_BAD = 0xFFFF5A4A;
	private static final int COL_SCANLINE = 0x22001A05;

	private static final int GUI_W = 356;
	private static final int GUI_H = 262;

	private enum Tab {
		FLIGHT("FLIGHT"), NAVIGATION("NAV"), SYSTEMS("SYSTEMS"), DOORS("DOORS"), COMMS("COMMS");

		final String label;

		Tab(String label) {
			this.label = label;
		}
	}

	private final ITARDISLevel tardis;
	private Tab tab = Tab.FLIGHT;

	private int leftPos, topPos;

	// Navigation tab widgets
	private EditBox destXBox, destYBox, destZBox, destDimBox;

	// Comms tab widgets
	private EditBox commsDimBox, commsMessageBox;

	public TARDISTerminalScreen(ITARDISLevel tardis) {
		super(Component.literal("TARDIS Terminal"));
		this.tardis = tardis;
	}

	@Override
	protected void init() {
		this.leftPos = (this.width - GUI_W) / 2;
		this.topPos = (this.height - GUI_H) / 2;

		addTabButtons();
		buildTabWidgets();
	}

	// Widget construction

	private void addTabButtons() {
		int x = leftPos + 4;
		int y = topPos + 20;
		int w = (GUI_W - 8) / Tab.values().length;

		for (Tab t : Tab.values()) {
			addRenderableWidget(
					Button.builder(Component.literal(t.label), b -> switchTab(t)).bounds(x, y, w - 2, 14).build());
			x += w;
		}
	}

	private void switchTab(Tab t) {
		this.tab = t;
		// Drop everything except the screen chrome (title/tab buttons stay because
		// we rebuild the whole widget list below).
		clearWidgets();
		addTabButtons();
		buildTabWidgets();
	}

	private void buildTabWidgets() {
		int x = leftPos + 10;
		int contentTop = topPos + 40;

		switch (tab) {
			case FLIGHT -> buildFlightTab(x, contentTop);
			case NAVIGATION -> buildNavigationTab(x, contentTop);
			case SYSTEMS -> buildSystemsTab(x, contentTop);
			case DOORS -> buildDoorsTab(x, contentTop);
			case COMMS -> buildCommsTab(x, contentTop);
		}
	}

	private void buildFlightTab(int x, int y) {
		int rowY = y + 82;

		addRenderableWidget(actionButton("DEMATERIALIZE", x, rowY, 110, Action.DEMATERIALIZE));
		addRenderableWidget(actionButton("REMATERIALIZE", x + 114, rowY, 110, Action.REMATERIALIZE));
		addRenderableWidget(actionButton("FORCE LAND", x + 228, rowY, 110, Action.FORCE_LAND));

		rowY += 18;
		addRenderableWidget(actionButton("COORD LOCK", x, rowY, 110, Action.TOGGLE_COORD_LOCK));
		addRenderableWidget(actionButton("VORTEX ANCHOR", x + 114, rowY, 110, Action.TOGGLE_VORTEX_ANCHOR));
		addRenderableWidget(actionButton("ENGINE BRAKE", x + 228, rowY, 110, Action.TOGGLE_ENGINE_BRAKE));

		rowY += 18;
		addRenderableWidget(actionButton("APC", x, rowY, 110, Action.TOGGLE_APC));
		addRenderableWidget(actionButton("STABILIZERS", x + 114, rowY, 110, Action.TOGGLE_STABILIZERS));
		addRenderableWidget(actionButton("SIMPLE MODE", x + 228, rowY, 110, Action.TOGGLE_SIMPLE_MODE));

		rowY += 18;
		addRenderableWidget(actionButton("ARTRON PACKET OUTPUT -", x, rowY, 70, Action.ARTRON_PACKET_DOWN));
		addRenderableWidget(actionButton("ARTRON PACKET OUTPUT +", x + 74, rowY, 70, Action.ARTRON_PACKET_UP));
		addRenderableWidget(
				actionButton("CYCLE TERM. PROTOCOL", x + 152, rowY, 186, Action.CYCLE_TERMINATION_PROTOCOL));
	}

	private void buildNavigationTab(int x, int y) {
		SpaceTimeCoordinate dest = tardis.GetNavigationalData().getDestination();

		int boxY = y + 76;
		int boxW = 60;
		destXBox = editBox(x, boxY, boxW, String.valueOf((int) dest.GetX()));
		destYBox = editBox(x + 64, boxY, boxW, String.valueOf((int) dest.GetY()));
		destZBox = editBox(x + 128, boxY, boxW, String.valueOf((int) dest.GetZ()));
		destDimBox = editBox(x + 192, boxY, 146,
				dest.getLevel() == null ? "minecraft:overworld" : dest.getLevel().dimension().location().toString());
		addRenderableWidget(destXBox);
		addRenderableWidget(destYBox);
		addRenderableWidget(destZBox);
		addRenderableWidget(destDimBox);

		addRenderableWidget(Button.builder(Component.literal("SET DESTINATION"), b -> sendSetDestination())
				.bounds(x, boxY + 18, 338, 16).build());

		int rowY = boxY + 42;
		addRenderableWidget(actionButton("X -", x, rowY, 54, Action.NUDGE_NEG_X));
		addRenderableWidget(actionButton("X +", x + 58, rowY, 54, Action.NUDGE_POS_X));
		addRenderableWidget(actionButton("Y -", x + 116, rowY, 54, Action.NUDGE_NEG_Y));
		addRenderableWidget(actionButton("Y +", x + 174, rowY, 54, Action.NUDGE_POS_Y));
		addRenderableWidget(actionButton("Z -", x + 232, rowY, 50, Action.NUDGE_NEG_Z));
		addRenderableWidget(actionButton("Z +", x + 286, rowY, 52, Action.NUDGE_POS_Z));

		rowY += 20;
		addRenderableWidget(actionButton("INCREMENT -", x, rowY, 110, Action.INCREMENT_DOWN));
		addRenderableWidget(actionButton("INCREMENT +", x + 114, rowY, 110, Action.INCREMENT_UP));
		addRenderableWidget(actionButton("CYCLE FACING", x + 228, rowY, 110, Action.CYCLE_DESTINATION_FACING));

		rowY += 18;
		addRenderableWidget(actionButton("RECALL PREVIOUS LOCATION", x, rowY, 338, Action.RECALL_PREVIOUS_LOCATION));
	}

	private void buildSystemsTab(int x, int y) {
		int rowY = y + 60;
		addRenderableWidget(actionButton("POWERED", x, rowY, 110, Action.TOGGLE_POWERED));
		addRenderableWidget(actionButton("REFUELING", x + 114, rowY, 110, Action.TOGGLE_REFUELING));
		addRenderableWidget(actionButton("DISCO MODE", x + 228, rowY, 110, Action.TOGGLE_DISCO));

		rowY += 18;
		addRenderableWidget(actionButton("ALARMS", x, rowY, 110, Action.TOGGLE_ALARMS));
		addRenderableWidget(actionButton("OPERATOR MODE", x + 114, rowY, 110, Action.TOGGLE_OPERATOR));
		addRenderableWidget(actionButton("CYCLE EXTERIOR", x + 228, rowY, 110, Action.CYCLE_EXTERIOR));

		rowY += 26;
		addRenderableWidget(actionButton("LIGHT -", x, rowY, 54, Action.LIGHT_DOWN));
		addRenderableWidget(actionButton("LIGHT +", x + 58, rowY, 54, Action.LIGHT_UP));
		addRenderableWidget(actionButton("GRAV -", x + 116, rowY, 54, Action.GRAVITY_DOWN));
		addRenderableWidget(actionButton("GRAV +", x + 174, rowY, 54, Action.GRAVITY_UP));
		addRenderableWidget(actionButton("O2 -", x + 232, rowY, 50, Action.OXYGEN_DOWN));
		addRenderableWidget(actionButton("O2 +", x + 286, rowY, 52, Action.OXYGEN_UP));

		rowY += 18;
		addRenderableWidget(actionButton("CYCLE INTERIOR HUM", x, rowY, 338, Action.HUM_CYCLE));
	}

	private void buildDoorsTab(int x, int y) {
		int rowY = y + 44;
		addRenderableWidget(actionButton("CYCLE DOOR STATE", x, rowY, 338, Action.CYCLE_DOOR_STATE));
	}

	private void buildCommsTab(int x, int y) {
		int boxY = y + 148;
		commsDimBox = editBox(x, boxY, 110, "");
		commsDimBox.setHint(Component.literal("dimension"));
		commsMessageBox = editBox(x + 114, boxY, 224, "");
		commsMessageBox.setHint(Component.literal("message"));
		addRenderableWidget(commsDimBox);
		addRenderableWidget(commsMessageBox);

		addRenderableWidget(Button.builder(Component.literal("SEND"), b -> sendCommsMessage())
				.bounds(x, boxY + 18, 338, 16).build());
	}

	private EditBox editBox(int x, int y, int w, String initial) {
		EditBox box = new EditBox(this.font, x, y, w, 16, Component.empty());
		box.setValue(initial);
		box.setMaxLength(64);
		return box;
	}

	private Button actionButton(String label, int x, int y, int w, Action action) {
		return Button.builder(Component.literal(label), b -> send(action)).bounds(x, y, w, 16).build();
	}

	// Networking

	private void send(Action action) {
		Networking.sendToServer(new TerminalActionPacketC2S(action));
	}

	private void sendSetDestination() {
		try {
			int bx = Integer.parseInt(destXBox.getValue().trim());
			int by = Integer.parseInt(destYBox.getValue().trim());
			int bz = Integer.parseInt(destZBox.getValue().trim());
			ResourceLocation dim = ResourceLocation.tryParse(destDimBox.getValue().trim());
			if (dim == null)
				return;

			Networking.sendToServer(new TerminalSetDestinationPacketC2S(new BlockPos(bx, by, bz), dim));
		} catch (NumberFormatException ignored) {
			// Bad input in the coordinate boxes - just ignore, terminal doesn't crash.
		}
	}

	private void sendCommsMessage() {
		String message = commsMessageBox.getValue().trim();
		ResourceLocation dim = ResourceLocation.tryParse(commsDimBox.getValue().trim());
		if (message.isEmpty() || dim == null)
			return;

		Networking.sendToServer(new TerminalSendMessagePacketC2S(message, dim));
		commsMessageBox.setValue("");
	}

	// Rendering

	@Override
	public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float partialTick) {
		renderBackground(gfx);
		drawFrame(gfx);
		drawTitle(gfx);
		super.render(gfx, mouseX, mouseY, partialTick);
		drawContent(gfx);
		drawScanlines(gfx);
	}

	private void drawFrame(GuiGraphics gfx) {
		gfx.fill(leftPos - 2, topPos - 2, leftPos + GUI_W + 2, topPos + GUI_H + 2, COL_FRAME_LIGHT);
		gfx.fill(leftPos, topPos, leftPos + GUI_W, topPos + GUI_H, COL_BG);
		gfx.fill(leftPos, topPos, leftPos + GUI_W, topPos + 18, COL_FRAME);
	}

	private void drawTitle(GuiGraphics gfx) {
		gfx.drawCenteredString(this.font, "T.A.R.D.I.S. TERMINAL", leftPos + GUI_W / 2, topPos + 5, COL_TEXT);
	}

	private void drawScanlines(GuiGraphics gfx) {
		for (int sy = topPos; sy < topPos + GUI_H; sy += 2) {
			gfx.fill(leftPos, sy, leftPos + GUI_W, sy + 1, COL_SCANLINE);
		}
	}

	private void drawContent(GuiGraphics gfx) {
		int x = leftPos + 10;
		int y = topPos + 40;

		switch (tab) {
			case FLIGHT -> drawFlightContent(gfx, x, y);
			case NAVIGATION -> drawNavigationContent(gfx, x, y);
			case SYSTEMS -> drawSystemsContent(gfx, x, y);
			case DOORS -> drawDoorsContent(gfx, x, y);
			case COMMS -> drawCommsContent(gfx, x, y);
		}
	}

	private void line(GuiGraphics gfx, String text, int x, int y, int color) {
		gfx.drawString(this.font, text, x, y, color, false);
	}

	private void drawFlightContent(GuiGraphics gfx, int x, int y) {
		boolean inFlight = tardis.GetFlightData().isInFlight();
		boolean takingOff = tardis.GetFlightData().IsTakingOff();
		String status = inFlight ? "IN FLIGHT" : takingOff ? "TAKING OFF" : "LANDED";
		int statusColor = inFlight ? COL_TEXT_WARN : takingOff ? COL_TEXT_WARN : COL_TEXT;

		line(gfx, "STATUS: " + status, x, y, statusColor);
		line(gfx, "CAN TAKEOFF: " + yesNo(tardis.CanTakeoff()) + "   CAN FLY: " + yesNo(tardis.CanFly()), x, y + 12,
				COL_TEXT_DIM);
		line(gfx, "TICKS IN FLIGHT: " + tardis.GetFlightData().getTicksInFlight() + "   DRIFT: "
				+ tardis.GetFlightData().getDrift(), x, y + 24, COL_TEXT_DIM);

		long arrival = tardis.GetFlightData().getTicksUntilArrival();
		line(gfx, "ETA: " + (arrival == Long.MAX_VALUE ? "N/A (anchored)" : arrival + " ticks"), x, y + 36,
				COL_TEXT_DIM);

		String eventName = tardis.getCurrentFlightEvent() == null
				? "none"
				: tardis.getCurrentFlightEvent().getClass().getSimpleName();
		line(gfx, "FLIGHT EVENT: " + eventName, x, y + 48, COL_TEXT_DIM);

		line(gfx, "ARTRON OUTPUT: " + tardis.GetData().getControlData().GetArtronPacketOutput() + "   TERM. PROTOCOL: "
				+ tardis.GetFlightData().getFlightTerminationProtocol(), x, y + 60, COL_TEXT_DIM);
	}

	private void drawNavigationContent(GuiGraphics gfx, int x, int y) {
		SpaceTimeCoordinate loc = tardis.GetNavigationalData().getLocation();
		SpaceTimeCoordinate dest = tardis.GetNavigationalData().getDestination();
		SpaceTimeCoordinate prev = tardis.GetNavigationalData().GetPreviousLocation();

		line(gfx, "LOCATION:    " + coordString(loc), x, y, COL_TEXT);
		line(gfx, "DESTINATION: " + coordString(dest) + "  FACING: " + tardis.GetNavigationalData().getFacing(), x,
				y + 12, COL_TEXT);
		line(gfx, "PREVIOUS:    " + coordString(prev), x, y + 24, COL_TEXT_DIM);
		line(gfx, "INCREMENT: " + tardis.GetNavigationalData().getIncrement() + "   DEST. FACING: "
				+ tardis.GetNavigationalData().getDestinationFacing(), x, y + 36, COL_TEXT_DIM);

		line(gfx, "ENTER NEW DESTINATION (X / Y / Z / DIMENSION):", x, y + 62, COL_TEXT_DIM);
	}

	private void drawSystemsContent(GuiGraphics gfx, int x, int y) {
		line(gfx, "POWER: " + tardis.getEnergy().getPower() + "   POWERED: " + yesNo(tardis.GetData().isPowered()), x,
				y, tardis.GetData().isPowered() ? COL_TEXT : COL_TEXT_BAD);
		line(gfx, "REFUELING: " + yesNo(tardis.GetData().isRefueling()) + "   DISCO: "
				+ yesNo(tardis.GetData().isIsDiscoMode()) + "   ALARMS: " + yesNo(tardis.GetData().isAlarmsState()), x,
				y + 12, COL_TEXT_DIM);
		line(gfx, "SPARKING: " + yesNo(tardis.GetData().isSparking()) + "   OPERATOR: " + yesNo(tardis.isOperator()), x,
				y + 24, COL_TEXT_DIM);
		line(gfx, "DEMAT. CIRCUIT: " + yesNo(
				tardis.GetData().getSubSystemsData().getDematerializationCircuit().isActivated(tardis.GetLevel())), x,
				y + 36, COL_TEXT_DIM);

		line(gfx,
				String.format("LIGHT: %.2f   GRAVITY: %.2f   OXYGEN: %.2f   HUM ID: %d",
						tardis.GetEnvironmentalData().getLightLevel(), tardis.GetEnvironmentalData().getGravityLevel(),
						tardis.GetEnvironmentalData().getOxygenLevel(), tardis.GetEnvironmentalData().getHum()),
				x, y + 48, COL_TEXT_DIM);
	}

	private void drawDoorsContent(GuiGraphics gfx, int x, int y) {
		int state = tardis.GetData().getDoorData().getDoorsOpen();
		String label = switch (state) {
			case 1 -> "SINGLE DOOR OPEN";
			case 2 -> "BOTH DOORS OPEN";
			default -> "CLOSED";
		};

		line(gfx, "DOOR STATE: " + label, x, y, COL_TEXT);
	}

	private void drawCommsContent(GuiGraphics gfx, int x, int y) {
		line(gfx, "INCOMING MESSAGES:", x, y, COL_TEXT);

		List<String> messages = tardis.getInterCommsMessages();
		int lineY = y + 14;
		int shown = 0;
		int start = Math.max(0, messages.size() - 8);
		for (int i = start; i < messages.size(); i++) {
			line(gfx, "> " + messages.get(i), x, lineY, COL_TEXT_DIM);
			lineY += 11;
			shown++;
		}
		if (shown == 0) {
			line(gfx, "(no messages received)", x, lineY, COL_TEXT_DIM);
		}

		line(gfx, "SEND TO (DIMENSION / MESSAGE):", x, y + 134, COL_TEXT_DIM);
	}

	private static String coordString(SpaceTimeCoordinate c) {
		return String.format("%.0f, %.0f, %.0f  [%s]", c.GetX(), c.GetY(), c.GetZ(),
				c.getLevel() == null ? "?" : c.getLevel().dimension().location().toString());
	}

	private static String yesNo(boolean b) {
		return b ? "YES" : "NO";
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
