/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.C2S.dimensions;

import java.util.function.Supplier;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

/**
 * Single generic C2S packet covering every no-argument terminal action
 * (toggles, triggers, +/- adjustments, nudges).
 */
public class TerminalActionPacketC2S {

	public enum Action {
		// Flight
		DEMATERIALIZE, REMATERIALIZE, FORCE_LAND, TOGGLE_COORD_LOCK, TOGGLE_VORTEX_ANCHOR, TOGGLE_ENGINE_BRAKE, TOGGLE_APC, TOGGLE_STABILIZERS, TOGGLE_SIMPLE_MODE, CYCLE_TERMINATION_PROTOCOL, ARTRON_PACKET_UP, ARTRON_PACKET_DOWN,

		// Navigation
		INCREMENT_UP, INCREMENT_DOWN, CYCLE_DESTINATION_FACING, RECALL_PREVIOUS_LOCATION, NUDGE_POS_X, NUDGE_NEG_X, NUDGE_POS_Y, NUDGE_NEG_Y, NUDGE_POS_Z, NUDGE_NEG_Z,

		// Systems / power
		TOGGLE_POWERED, TOGGLE_REFUELING, TOGGLE_DISCO, TOGGLE_ALARMS, TOGGLE_OPERATOR, LIGHT_UP, LIGHT_DOWN, GRAVITY_UP, GRAVITY_DOWN, OXYGEN_UP, OXYGEN_DOWN, HUM_CYCLE, CYCLE_EXTERIOR,

		// Doors
		CYCLE_DOOR_STATE
	}

	private final Action action;

	public TerminalActionPacketC2S(Action action) {
		this.action = action;
	}

	public TerminalActionPacketC2S(FriendlyByteBuf buf) {
		this.action = buf.readEnum(Action.class);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeEnum(action);
	}

	public static void handle(TerminalActionPacketC2S msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player == null)
				return;

			ITARDISLevel tardis = TARDISLevelCapability.GetTARDISCap(player.level());
			if (tardis == null)
				return;

			// TODO: hook this into a permission system!
			if (!hasPermission(tardis, player))
				return;

			switch (msg.action) {
				// ---- Flight ----
				case DEMATERIALIZE -> tardis.Dematerialize();
				case REMATERIALIZE -> tardis.Rematerialize();
				case FORCE_LAND -> tardis.FuckingLandAlreadyDammit();

				case TOGGLE_COORD_LOCK -> tardis.GetData().getControlData()
						.setCoordinateLock(!tardis.GetData().getControlData().isCoordinateLock());

				case TOGGLE_VORTEX_ANCHOR -> tardis.GetData().getControlData()
						.setVortexAnchor(!tardis.GetData().getControlData().isVortexAnchor());

				case TOGGLE_ENGINE_BRAKE ->
					tardis.GetData().getControlData().setBrakes(!tardis.GetData().getControlData().isEngineBrake());

				case TOGGLE_APC ->
					tardis.GetData().getControlData().setAPCState(!tardis.GetData().getControlData().isAPCState());

				case TOGGLE_STABILIZERS -> tardis.GetData().getControlData()
						.setStabilizers(!tardis.GetData().getControlData().isStabilizers());

				case TOGGLE_SIMPLE_MODE ->
					tardis.GetData().getControlData().setSimpleMode(!tardis.GetData().getControlData().isSimpleMode());

				case CYCLE_TERMINATION_PROTOCOL -> tardis.GetFlightData().setFlightTerminationProtocol(
						com.code.tama.tts.core.registries.tardis.FlightTerminationProtocolRegistry
								.CycleProt(tardis.GetFlightData().getFlightTerminationProtocol()));

				case ARTRON_PACKET_UP -> tardis.GetData().getControlData()
                        .setArtronPacketOutput(tardis.GetData().getControlData().GetArtronPacketOutput() + 1);
				case ARTRON_PACKET_DOWN -> tardis.GetData().getControlData().setArtronPacketOutput(
                    Math.max(0, tardis.GetData().getControlData().GetArtronPacketOutput() - 1));

				// ---- Navigation ----
				case INCREMENT_UP ->
					tardis.GetNavigationalData().setIncrement(tardis.GetNavigationalData().GetNextIncrement());
				case INCREMENT_DOWN ->
					tardis.GetNavigationalData().setIncrement(tardis.GetNavigationalData().GetPreviousIncrement());
				case CYCLE_DESTINATION_FACING -> tardis.GetNavigationalData()
						.setDestinationFacing(tardis.GetNavigationalData().NextDestinationFacing());
				case RECALL_PREVIOUS_LOCATION -> tardis.GetNavigationalData()
						.forceSetDestination(tardis.GetNavigationalData().GetPreviousLocation());

				case NUDGE_POS_X -> nudge(tardis, tardis.GetNavigationalData().getIncrement(), 0, 0);
				case NUDGE_NEG_X -> nudge(tardis, -tardis.GetNavigationalData().getIncrement(), 0, 0);
				case NUDGE_POS_Y -> nudge(tardis, 0, tardis.GetNavigationalData().getIncrement(), 0);
				case NUDGE_NEG_Y -> nudge(tardis, 0, -tardis.GetNavigationalData().getIncrement(), 0);
				case NUDGE_POS_Z -> nudge(tardis, 0, 0, tardis.GetNavigationalData().getIncrement());
				case NUDGE_NEG_Z -> nudge(tardis, 0, 0, -tardis.GetNavigationalData().getIncrement());

				// ---- Systems / power ----
				// Uses the crash-aware custom setter, not the plain lombok one.
				case TOGGLE_POWERED -> tardis.GetData().SetPowered(!tardis.GetData().isPowered());
				case TOGGLE_REFUELING -> tardis.GetData().setRefueling(!tardis.GetData().isRefueling());
				case TOGGLE_DISCO -> tardis.GetData().setIsDiscoMode(!tardis.GetData().isIsDiscoMode());
				case TOGGLE_ALARMS -> tardis.GetData().setAlarmsState(!tardis.GetData().isAlarmsState());
				case TOGGLE_OPERATOR -> tardis.setOperator(!tardis.isOperator());

				case LIGHT_UP ->
					tardis.GetEnvironmentalData().SetLightLevel(tardis.GetEnvironmentalData().getLightLevel() + 0.1f);
				case LIGHT_DOWN ->
					tardis.GetEnvironmentalData().SetLightLevel(tardis.GetEnvironmentalData().getLightLevel() - 0.1f);
				case GRAVITY_UP -> tardis.GetEnvironmentalData()
						.setGravityLevel(tardis.GetEnvironmentalData().getGravityLevel() + 0.02f);
				case GRAVITY_DOWN -> tardis.GetEnvironmentalData()
						.setGravityLevel(tardis.GetEnvironmentalData().getGravityLevel() - 0.02f);
				case OXYGEN_UP -> tardis.GetEnvironmentalData()
						.setOxygenLevel(tardis.GetEnvironmentalData().getOxygenLevel() + 0.05f);
				case OXYGEN_DOWN -> tardis.GetEnvironmentalData()
						.setOxygenLevel(tardis.GetEnvironmentalData().getOxygenLevel() - 0.05f);
				case HUM_CYCLE -> tardis.GetEnvironmentalData().setHum(tardis.GetEnvironmentalData().getHum() + 1);

				case CYCLE_EXTERIOR -> tardis.GetData().CycleVariant();

				// ---- Doors ----
				case CYCLE_DOOR_STATE -> {
					int next = (tardis.GetData().getDoorData().getDoorsOpen() + 1) % 3;
					tardis.GetData().getDoorData().setDoorsOpen(next);
				}
			}

			tardis.UpdateClient(DataUpdateValues.ALL);
		});
		ctx.get().setPacketHandled(true);
	}

	private static void nudge(ITARDISLevel tardis, double dx, double dy, double dz) {
		SpaceTimeCoordinate dest = tardis.GetNavigationalData().getDestination();
		dest.AddX(dx).AddY(dy).AddZ(dz);
		tardis.GetNavigationalData().forceSetDestination(dest);
	}

	/**
	 * TODO: replace with a real permission system!
	 */
	private static boolean hasPermission(ITARDISLevel tardis, ServerPlayer player) {
		return true;
	}
}
