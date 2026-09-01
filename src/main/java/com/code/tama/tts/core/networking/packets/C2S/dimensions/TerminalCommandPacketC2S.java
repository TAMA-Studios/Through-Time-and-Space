/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.networking.packets.C2S.dimensions;

import java.util.function.Supplier;

import com.code.tama.tts.client.gui.terminal.ManPages;
import com.code.tama.tts.core.networking.Networking;
import com.code.tama.tts.core.networking.packets.S2C.dimensions.TerminalResponsePacketS2C;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.data.tardis.DataUpdateValues;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

/**
 * Runs a single typed console command against the sending player's TARDIS
 * capability and replies with a {@link TerminalResponsePacketS2C} containing
 * whatever the console should print.
 */
public class TerminalCommandPacketC2S {

	private final String command;

	public TerminalCommandPacketC2S(String command) {
		this.command = command;
	}

	public TerminalCommandPacketC2S(FriendlyByteBuf buf) {
		this.command = buf.readUtf(256);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeUtf(command, 256);
	}

	public static void handle(TerminalCommandPacketC2S msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer player = ctx.get().getSender();
			if (player == null)
				return;

			ITARDISLevel tardis = TARDISLevelCapability.GetTARDISCap(player.level());
			if (tardis == null) {
				reply(player, "ERR: no TARDIS capability found on this level.");
				return;
			}

			if (!hasPermission(tardis, player)) {
				reply(player, "ERR: username " + player.getName()
						+ " is not in the flyers file. this incident will be reported");
				return;
			}

			String response;
			try {
				response = run(tardis, msg.command.trim());
			} catch (Exception e) {
				response = "ERR: " + e.getMessage();
			}

			tardis.UpdateClient(DataUpdateValues.ALL);
			reply(player, response);
		});
		ctx.get().setPacketHandled(true);
	}

	private static void reply(ServerPlayer player, String message) {
		Networking.sendToPlayer(player, new TerminalResponsePacketS2C(message));
	}

	// Command parsing

	private static String run(ITARDISLevel t, String raw) {
		if (raw.isEmpty())
			return "";

		String[] args = raw.split("\\s+");
		String cmd = args[0].toLowerCase();

		return switch (cmd) {
			case "help" -> HELP_TEXT;
			case "status" -> status(t);

			case "takeoff", "dematerialize" -> {
				t.Dematerialize();
				yield "Dematerialization sequence started.";
			}
			case "land", "rematerialize" -> {
				t.Rematerialize();
				yield "Rematerialization sequence started.";
			}
			case "forceland" -> {
				t.FuckingLandAlreadyDammit();
				yield "Forced landing executed.";
			}

			case "power" -> {
				boolean target = resolveBool(args, t.GetData().isPowered());
				t.GetData().SetPowered(target);
				yield "Power: " + onOff(target);
			}
			case "refuel" -> {
				boolean target = resolveBool(args, t.GetData().isRefueling());
				t.GetData().setRefueling(target);
				yield "Refueling: " + onOff(target);
			}
			case "disco" -> {
				boolean target = resolveBool(args, t.GetData().isIsDiscoMode());
				t.GetData().setIsDiscoMode(target);
				yield "Disco mode: " + onOff(target);
			}
			case "alarms" -> {
				boolean target = resolveBool(args, t.GetData().isAlarmsState());
				t.GetData().setAlarmsState(target);
				yield "Alarms: " + onOff(target);
			}
			case "operator" -> {
				boolean target = resolveBool(args, t.isOperator());
				t.setOperator(target);
				yield "Operator mode: " + onOff(target);
			}
			case "coordlock" -> {
				boolean target = resolveBool(args, t.GetData().getControlData().isCoordinateLock());
				t.GetData().getControlData().setCoordinateLock(target);
				yield "Coordinate lock: " + onOff(target);
			}
			case "vortexanchor" -> {
				boolean target = resolveBool(args, t.GetData().getControlData().isVortexAnchor());
				t.GetData().getControlData().setVortexAnchor(target);
				yield "Vortex anchor: " + onOff(target);
			}
			case "enginebrake" -> {
				boolean target = resolveBool(args, t.GetData().getControlData().isEngineBrake());
				t.GetData().getControlData().setBrakes(target);
				yield "Engine brake: " + onOff(target);
			}
			case "apc" -> {
				boolean target = resolveBool(args, t.GetData().getControlData().isAPCState());
				t.GetData().getControlData().setAPCState(target);
				yield "APC: " + onOff(target);
			}
			case "stabilizers" -> {
				boolean target = resolveBool(args, t.GetData().getControlData().isStabilizers());
				t.GetData().getControlData().setStabilizers(target);
				yield "Stabilizers: " + onOff(target);
			}
			case "simplemode" -> {
				boolean target = resolveBool(args, t.GetData().getControlData().isSimpleMode());
				t.GetData().getControlData().setSimpleMode(target);
				yield "Simple mode: " + onOff(target);
			}

			case "artron" -> {
				int current = t.GetData().getControlData().GetArtronPacketOutput();
				int target = resolveInt(args, current, 1);
				t.GetData().getControlData().setArtronPacketOutput(Math.max(0, target));
				yield "Artron packet output: " + Math.max(0, target);
			}

			case "termprotocol" -> {
				if (args.length > 1 && args[1].equalsIgnoreCase("cycle")) {
					// ASSUMED: mirrors the confirmed ExteriorsRegistry.Cycle(...) pattern.
					t.GetFlightData().setFlightTerminationProtocol(
							com.code.tama.tts.core.registries.tardis.FlightTerminationProtocolRegistry
									.CycleProt(t.GetFlightData().getFlightTerminationProtocol()));
				}
				yield "Flight termination protocol: " + t.GetFlightData().getFlightTerminationProtocol();
			}

			case "dest", "destination" -> destination(t, args);
			case "nudge" -> nudge(t, args);
			case "facing" -> {
				if (args.length > 1 && args[1].equalsIgnoreCase("cycle")) {
					t.GetNavigationalData().setDestinationFacing(t.GetNavigationalData().NextDestinationFacing());
				}
				yield "Destination facing: " + t.GetNavigationalData().getDestinationFacing();
			}
			case "increment" -> increment(t, args);
			case "recall" -> {
				t.GetNavigationalData().forceSetDestination(t.GetNavigationalData().GetPreviousLocation());
				yield "Destination set to previous location.";
			}

			case "light" -> {
				float current = t.GetEnvironmentalData().getLightLevel();
				float target = resolveFloat(args, current, 0.1f);
				t.GetEnvironmentalData().SetLightLevel(target);
				yield "Light level: " + t.GetEnvironmentalData().getLightLevel();
			}
			case "gravity" -> {
				float current = t.GetEnvironmentalData().getGravityLevel();
				float target = resolveFloat(args, current, 0.02f);
				t.GetEnvironmentalData().setGravityLevel(target);
				yield "Gravity level: " + target;
			}
			case "oxygen" -> {
				float current = t.GetEnvironmentalData().getOxygenLevel();
				float target = resolveFloat(args, current, 0.05f);
				t.GetEnvironmentalData().setOxygenLevel(target);
				yield "Oxygen level: " + target;
			}
			case "hum" -> {
				if (args.length > 1 && args[1].equalsIgnoreCase("cycle")) {
					t.GetEnvironmentalData().setHum(t.GetEnvironmentalData().getHum() + 1);
				} else if (args.length > 1) {
					t.GetEnvironmentalData().setHum(Integer.parseInt(args[1]));
				}
				yield "Interior hum ID: " + t.GetEnvironmentalData().getHum();
			}

			case "door" -> door(t, args);

			case "exterior" -> {
				if (args.length > 1 && args[1].equalsIgnoreCase("cycle")) {
					t.GetData().CycleVariant();
				}
				yield "Exterior model: " + t.GetData().getExteriorModel();
			}

			case "comms" -> comms(t, args, raw);

			case "man" -> args.length > 1
					? ManPages.getHelpText(java.util.Arrays.copyOfRange(args, 1, args.length))
					: "usage: man <command>";

			default -> "ERR: unknown command '" + cmd + "'. Type 'help' for a list.";
		};
	}

	private static String status(ITARDISLevel t) {
		return "--- TARDIS STATUS ---\n" + "Flight: "
				+ (t.GetFlightData().isInFlight()
						? "IN FLIGHT"
						: t.GetFlightData().IsTakingOff() ? "TAKING OFF" : "LANDED")
				+ '\n' + "Can takeoff: " + t.CanTakeoff() + "  Can fly: " + t.CanFly() + '\n' + "Power: "
				+ t.getEnergy().getPower() + "  Powered: " + t.GetData().isPowered() + '\n' + "Location:    "
				+ coordString(t.GetNavigationalData().getLocation()) + '\n' + "Destination: "
				+ coordString(t.GetNavigationalData().getDestination()) + '\n' + "Increment: "
				+ t.GetNavigationalData().getIncrement() + '\n' + "Door state: "
				+ t.GetData().getDoorData().getDoorsOpen();
	}

	private static String destination(ITARDISLevel t, String[] args) {
		if (args.length < 4)
			return "usage: dest <x> <y> <z> [dimension]";

		try {
			int x = Integer.parseInt(args[1]);
			int y = Integer.parseInt(args[2]);
			int z = Integer.parseInt(args[3]);

			ResourceKey<Level> levelKey;
			if (args.length > 4) {
				ResourceLocation rl = ResourceLocation.tryParse(args[4]);
				if (rl == null)
					return "ERR: invalid dimension '" + args[4] + "'";
				levelKey = ResourceKey.create(Registries.DIMENSION, rl);
			} else {
				levelKey = t.GetNavigationalData().getExteriorDimensionKey();
			}

			SpaceTimeCoordinate coordinate = new SpaceTimeCoordinate(new BlockPos(x, y, z), levelKey);
			t.GetNavigationalData().forceSetDestination(coordinate);
			return "Destination set to " + coordString(coordinate);
		} catch (NumberFormatException e) {
			return "ERR: x/y/z must be whole numbers.";
		}
	}

	private static String nudge(ITARDISLevel t, String[] args) {
		if (args.length < 2)
			return "usage: nudge <x|y|z> [amount]";

		double amount = t.GetNavigationalData().getIncrement();
		if (args.length > 2) {
			try {
				amount = Double.parseDouble(args[2]);
			} catch (NumberFormatException e) {
				return "ERR: amount must be a number.";
			}
		}

		SpaceTimeCoordinate dest = t.GetNavigationalData().getDestination();
		switch (args[1].toLowerCase()) {
			case "x" -> dest.AddX(amount);
			case "y" -> dest.AddY(amount);
			case "z" -> dest.AddZ(amount);
			default -> {
				return "ERR: axis must be x, y or z.";
			}
		}
		t.GetNavigationalData().forceSetDestination(dest);
		return "Destination nudged. New destination: " + coordString(dest);
	}

	private static String increment(ITARDISLevel t, String[] args) {
		if (args.length < 2)
			return "Increment: " + t.GetNavigationalData().getIncrement();

		if (args[1].equalsIgnoreCase("up")) {
			t.GetNavigationalData().setIncrement(t.GetNavigationalData().GetNextIncrement());
		} else if (args[1].equalsIgnoreCase("down")) {
			t.GetNavigationalData().setIncrement(t.GetNavigationalData().GetPreviousIncrement());
		} else {
			try {
				t.GetNavigationalData().setIncrement(Integer.parseInt(args[1]));
			} catch (NumberFormatException e) {
				return "ERR: increment must be 'up', 'down' or a number.";
			}
		}
		return "Increment: " + t.GetNavigationalData().getIncrement();
	}

	private static String door(ITARDISLevel t, String[] args) {
		if (args.length < 2) {
			return "Door state: " + t.GetData().getDoorData().getDoorsOpen();
		}
		int target = switch (args[1].toLowerCase()) {
			case "close", "closed" -> 0;
			case "open" -> 1;
			case "both" -> 2;
			case "cycle" -> (t.GetData().getDoorData().getDoorsOpen() + 1) % 3;
			default -> -1;
		};
		if (target < 0)
			return "usage: door <open|close|both|cycle>";

		t.GetData().getDoorData().setDoorsOpen(target);
		return "Door state: " + target;
	}

	private static String comms(ITARDISLevel t, String[] args, String raw) {
		if (args.length < 2)
			return "usage: comms send <dimension> <message>  |  comms log";

		if (args[1].equalsIgnoreCase("log")) {
			var messages = t.getInterCommsMessages();
			if (messages.isEmpty())
				return "(no messages received)";
			StringBuilder sb = new StringBuilder();
			int start = Math.max(0, messages.size() - 10);
			for (int i = start; i < messages.size(); i++) {
				sb.append("> ").append(messages.get(i));
				if (i < messages.size() - 1)
					sb.append('\n');
			}
			return sb.toString();
		}

		if (args[1].equalsIgnoreCase("send")) {
			if (args.length < 4)
				return "usage: comms send <dimension> <message>";

			ResourceLocation dim = ResourceLocation.tryParse(args[2]);
			if (dim == null)
				return "ERR: invalid dimension '" + args[2] + "'";

			// Reconstruct the message from the remaining tokens.
			String message = raw.substring(raw.indexOf(args[3]));
			t.sendInterCommMessage(message, dim);
			return "Message sent to " + dim;
		}

		return "usage: comms send <dimension> <message>  |  comms log";
	}

	// Small parsing helpers

	private static boolean resolveBool(String[] args, boolean current) {
		if (args.length < 2)
			return !current;
		return switch (args[1].toLowerCase()) {
			case "on", "true", "yes" -> true;
			case "off", "false", "no" -> false;
			default -> !current;
		};
	}

	private static int resolveInt(String[] args, int current, int step) {
		if (args.length < 2)
			return current;
		if (args[1].equals("+"))
			return current + step;
		if (args[1].equals("-"))
			return current - step;
		try {
			return Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			return current;
		}
	}

	private static float resolveFloat(String[] args, float current, float step) {
		if (args.length < 2)
			return current;
		if (args[1].equals("+"))
			return current + step;
		if (args[1].equals("-"))
			return current - step;
		try {
			return Float.parseFloat(args[1]);
		} catch (NumberFormatException e) {
			return current;
		}
	}

	private static String coordString(SpaceTimeCoordinate c) {
		return String.format("%.0f, %.0f, %.0f [%s]", c.GetX(), c.GetY(), c.GetZ(),
				c.getLevel() == null ? "?" : c.getLevel().dimension().location().toString());
	}

	private static String onOff(boolean b) {
		return b ? "ON" : "OFF";
	}

	/**
	 * TODO: replace with a real permission system!
	 */
	private static boolean hasPermission(ITARDISLevel tardis, ServerPlayer player) {
		return true;
	}

	private static final String HELP_TEXT = """
			Available commands:
			status                              full status readout
			takeoff / land / forceland
			power|refuel|disco|alarms|operator [on|off]
			coordlock|vortexanchor|enginebrake|apc|stabilizers|simplemode [on|off]
			artron [value|+|-]
			termprotocol cycle
			dest <x> <y> <z> [dimension]
			nudge <x|y|z> [amount]
			facing cycle
			increment [up|down|<value>]
			recall
			light|gravity|oxygen [value|+|-]
			hum [cycle|<id>]
			door <open|close|both|cycle>
			exterior cycle
			comms send <dimension> <message>
			comms log
			clear                               clear this screen
			man <command>                       full manual page for a command
			help""";
}