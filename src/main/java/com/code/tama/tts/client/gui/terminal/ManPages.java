/* (C) TAMA Studios 2025 */
package com.code.tama.tts.client.gui.terminal;

/**
 * Renders `man`-style manual pages for the console's commands.
 */
public class ManPages {

	public static String getHelpText(String[] args) {
		if (args.length == 0)
			return "usage: man <command>";

		return switch (args[0]) {
			case "help" -> manPage("help", """
					NAME
						help - display a summary of all available commands

					SYNOPSIS
						help

					DESCRIPTION
						Prints a short, one-line-per-command summary of every command the
						console understands. For the full manual on a specific command,
						including options and usage notes, use man instead:

								man <command>
					""");

			case "status" -> manPage("status", """
					NAME
						status - display a full TARDIS status readout

					SYNOPSIS
						status

					DESCRIPTION
						Prints a consolidated readout of the TARDIS's current state:
						flight status (landed / taking off / in flight), whether it is
						currently capable of taking off or continuing flight, power
						level and power state, current and destination coordinates, the
						active navigational increment, and the interior door state.

						Intended as a quick at-a-glance dashboard; for details on any one
						subsystem, run the relevant command with no arguments instead
						(e.g. door, light).
					""");

			case "takeoff", "dematerialize" -> manPage("takeoff", """
					NAME
						takeoff - initiate the TARDIS takeoff sequence

					SYNOPSIS
						takeoff
						dematerialize

					DESCRIPTION
						The takeoff command initiates the TARDIS flight sequence. Upon
						execution, the TARDIS will disengage from its current spatial
						coordinates and begin dematerialisation. Same as dematerialize.

						--help
							display this help and exit

						--version
							output version information and exit
					""");

			case "land", "rematerialize" -> manPage("land", """
					NAME
						land - initiate the TARDIS landing sequence

					SYNOPSIS
						land
						rematerialize

					DESCRIPTION
						Initiates rematerialisation at the current destination
						coordinates, bringing an in-progress flight to its normal
						conclusion. Has no effect if the TARDIS is not currently in
						flight. Same as rematerialize.
					""");

			case "forceland" -> manPage("forceland", """
					NAME
						forceland - force an immediate landing

					SYNOPSIS
						forceland

					DESCRIPTION
						Immediately forces the TARDIS to land, bypassing the normal
						rematerialisation sequence. Intended as a last-resort override
						for situations where land refuses to engage properly.

						WARNING
								This is an emergency control. It does not perform the
								usual pre-landing checks that land does - use it only
								when you have to.
					""");

			case "power" -> manPage("power", """
					NAME
						power - toggle main TARDIS power

					SYNOPSIS
						power [on|off]

					DESCRIPTION
						Turns the TARDIS's main power on or off. Called with no
						arguments, toggles the current state; on or off sets it
						explicitly.

						WARNING
								Cutting power while the TARDIS is in flight will cause it
								to crash. Land first.
					""");

			case "refuel" -> manPage("refuel", """
					NAME
						refuel - toggle the TARDIS refueling state

					SYNOPSIS
						refuel [on|off]

					DESCRIPTION
						Toggles whether the TARDIS is currently refueling. Called with
						no arguments, toggles the current state; on or off sets it
						explicitly.
					""");

			case "disco" -> manPage("disco", """
					NAME
						disco - toggle disco mode

					SYNOPSIS
						disco [on|off]

					DESCRIPTION
						Toggles disco mode: a purely cosmetic lighting effect with no
						bearing on flight performance, power draw, or anything else
						that matters. Called with no arguments, toggles the current
						state; on or off sets it explicitly.

						Popularity of this feature among certain Time Lords is a matter
						of historical record.
					""");

			case "alarms" -> manPage("alarms", """
					NAME
						alarms - toggle the internal alarm system

					SYNOPSIS
						alarms [on|off]

					DESCRIPTION
						Toggles the TARDIS's internal alarm/warning state, used to
						alert the crew to malfunctions, hazards, or other conditions
						worth shouting about. Called with no arguments, toggles the
						current state; on or off sets it explicitly.
					""");

			case "operator" -> manPage("operator", """
					NAME
						operator - mark this TARDIS as an Operator TARDIS

					SYNOPSIS
						operator [on|off]

					DESCRIPTION
						Marks (or unmarks) this TARDIS as an "Operator" TARDIS. This is
						intended for use by server operators, not regular players -
						it flags the TARDIS for whatever elevated handling the mod
						grants to operator-owned TARDISes elsewhere. Called with no
						arguments, toggles the current state; on or off sets it
						explicitly.
					""");

			case "coordlock" -> manPage("coordlock", """
					NAME
						coordlock - lock the destination coordinates

					SYNOPSIS
						coordlock [on|off]

					DESCRIPTION
						Toggles the coordinate lock. While engaged, destination changes
						made through interfaces that respect the lock are ignored.
						Called with no arguments, toggles the current state; on or off
						sets it explicitly.

						NOTE
								This console's own dest and nudge commands
								intentionally set the destination directly and are NOT
								blocked by this lock, since the terminal is treated as a
								trusted control surface. The lock exists to stop other,
								less deliberate interference.
					""");

			case "vortexanchor" -> manPage("vortexanchor", """
					NAME
						vortexanchor - anchor the TARDIS in the time vortex

					SYNOPSIS
						vortexanchor [on|off]

					DESCRIPTION
						Toggles the vortex anchor. While engaged, the TARDIS remains
						suspended in the time vortex rather than proceeding toward its
						destination - estimated time of arrival becomes undefined
						until the anchor is released. Called with no arguments, toggles
						the current state; on or off sets it explicitly.
					""");

			case "enginebrake" -> manPage("enginebrake", """
					NAME
						enginebrake - engage the engine brake

					SYNOPSIS
						enginebrake [on|off]

					DESCRIPTION
						Toggles the engine brake, arresting the TARDIS's progress
						through the vortex without triggering a full emergency
						landing. Called with no arguments, toggles the current state;
						on or off sets it explicitly.
					""");

			// TODO: leaving this as a bare template, needa re-read the TARDIS manual page
			// on the Artron Packet Controller before writing this one.
			case "apc" -> manPage("apc", """
					NAME
						apc - TODO

					SYNOPSIS
						apc [on|off]

					DESCRIPTION
						TODO: document what this control actually does.
					""");

			case "stabilizers" -> manPage("stabilizers", """
					NAME
						stabilizers - toggle flight stabilizers

					SYNOPSIS
						stabilizers [on|off]

					DESCRIPTION
						Toggles the flight stabilizers, intended to reduce turbulence
						and drift encountered during random flight events. Called with
						no arguments, toggles the current state; on or off sets it
						explicitly.
					""");

			case "simplemode" -> manPage("simplemode", """
					NAME
						simplemode - toggle simplified flight assistance

					SYNOPSIS
						simplemode [on|off]

					DESCRIPTION
						Toggles simplified flight mode, intended to make the TARDIS
						easier to fly by automating portions of the flight sequence
						that would otherwise need manual attention. Called with no
						arguments, toggles the current state; on or off sets it
						explicitly.
					""");

			// TODO: same as apc - "Artron Packet Output"
			case "artron_packet" -> manPage("artron_packet", """
					NAME
						artron_packet - TODO

					SYNOPSIS
						artron_packet [value|+|-]

					DESCRIPTION
						TODO: document what Artron Packet Output actually controls.
					""");

			case "termprotocol" -> manPage("termprotocol", """
					NAME
						termprotocol - cycle the flight termination protocol

					SYNOPSIS
						termprotocol cycle

					DESCRIPTION
						Cycles through the available flight termination protocols,
						which govern how the TARDIS resolves the end of a flight (or an
						interruption to one). Called with no arguments, just reports
						the currently active protocol without changing it.
					""");

			case "dest", "destination" -> manPage("dest", """
					NAME
						dest - set the TARDIS destination

					SYNOPSIS
						dest <x> <y> <z> [dimension]
						destination <x> <y> <z> [dimension]

					DESCRIPTION
						Sets the destination to the exact block coordinates given. If
						dimension is omitted, the TARDIS's current exterior dimension
						is used. Bypasses the coordinate lock (see coordlock).

						EXAMPLES
								dest 100 64 -32
								dest 0 128 0 minecraft:the_end
					""");

			case "nudge" -> manPage("nudge", """
					NAME
						nudge - adjust the destination along one axis

					SYNOPSIS
						nudge <x|y|z> [amount]

					DESCRIPTION
						Adjusts the current destination along a single axis by amount
						blocks. If amount is omitted, the current navigational
						increment is used instead (see increment).

						EXAMPLES
								nudge x
								nudge y -50
					""");

			case "facing" -> manPage("facing", """
					NAME
						facing - cycle the destination facing

					SYNOPSIS
						facing cycle

					DESCRIPTION
						Rotates the destination facing direction one step clockwise.
						Called with no arguments, reports the current destination
						facing without changing it.
					""");

			case "increment" -> manPage("increment", """
					NAME
						increment - view or set the navigational step size

					SYNOPSIS
						increment [up|down|<value>]

					DESCRIPTION
						Controls the step size used by nudge and by the terminal's own
						+/- coordinate controls. up and down move through the
						TARDIS's standard increment steps (1, 10, 100, 1000, 10000,
						100000); a numeric value sets it directly. Called with no
						arguments, reports the current increment.
					""");

			case "recall" -> manPage("recall", """
					NAME
						recall - recall the previous location

					SYNOPSIS
						recall

					DESCRIPTION
						Sets the destination back to the TARDIS's previous location,
						effectively undoing the last flight. Does not itself initiate
						a flight - follow up with takeoff to actually travel there.
					""");

			case "light" -> manPage("light", """
					NAME
						light - view or adjust interior light level

					SYNOPSIS
						light [value|+|-]

					DESCRIPTION
						Sets the interior ambient light level to value, or nudges it
						up/down by a fixed step with + or -. Called with no arguments,
						reports the current light level without changing it.
					""");

			case "gravity" -> manPage("gravity", """
					NAME
						gravity - view or adjust interior gravity level

					SYNOPSIS
						gravity [value|+|-]

					DESCRIPTION
						Sets the interior gravity level to value, or nudges it up/down
						by a fixed step with + or -. Called with no arguments, reports
						the current gravity level without changing it.
					""");

			case "oxygen" -> manPage("oxygen", """
					NAME
						oxygen - view or adjust interior oxygen level

					SYNOPSIS
						oxygen [value|+|-]

					DESCRIPTION
						Sets the interior oxygen level to value, or nudges it up/down
						by a fixed step with + or -. Called with no arguments, reports
						the current oxygen level without changing it.
					""");

			case "hum" -> manPage("hum", """
					NAME
						hum - view, cycle, or set the interior ambient hum

					SYNOPSIS
						hum [cycle|<id>]

					DESCRIPTION
						Sets the interior ambient hum/drone sound to the given id, or
						advances it to the next one with cycle. Called with no
						arguments, reports the current hum id without changing it.
					""");

			case "door" -> manPage("door", """
					NAME
						door - control the interior door state

					SYNOPSIS
						door <open|close|both|cycle>

					DESCRIPTION
						Sets the interior door state directly (open, close, both), or
						advances to the next state with cycle. Called with no
						arguments, reports the current door state without changing it.
					""");

			case "exterior" -> manPage("exterior", """
					NAME
						exterior - cycle the exterior shell model

					SYNOPSIS
						exterior cycle

					DESCRIPTION
						Advances to the next available exterior shell model. Called
						with no arguments, reports the current exterior model without
						changing it.
					""");

			case "comms" -> manPage("comms", """
					NAME
						comms - inter-TARDIS communications

					SYNOPSIS
						comms send <dimension> <message>
						comms log

					DESCRIPTION
						send transmits message as an Inter-TARDIS Communications
						message to the TARDIS occupying dimension. log displays the
						most recent messages this TARDIS has received.
					""");

			default -> "No manual entry for '" + args[0] + "'";
		};
	}

	// Formatting

	private static String manPage(String command, String body) {
		String title = command.toUpperCase() + "(1)";
		String header = pad(title, "User Commands", title);
		String footer = pad("TTS coreutils 8.31", "Venurcury 10432", title);

		return header + "\n\n" + body.strip() + "\n\n" + AUTHOR_BLOCK + "\n\n" + footer;
	}

	private static String pad(String left, String center, String right) {
		int width = 62;
		int centerStart = (width - center.length()) / 2;
		StringBuilder sb = new StringBuilder();
		sb.append(left);
		while (sb.length() < centerStart)
			sb.append(' ');
		sb.append(center);
		while (sb.length() < width - right.length())
			sb.append(' ');
		sb.append(right);
		return sb.toString();
	}

	private static final String AUTHOR_BLOCK = """
			AUTHOR
				Written by Codiak Pendragon in collaboration with the Kasterborous
				Shipyards Software Division, under the supervision of the
				Gallifreyan Appointed Committee for Temporal Software
				Development (TSD).

			REPORTING BUGS
				TTS online documentation: <https://tama-studios.github.io/Through-Time-and-Space/>
				Report bugs to the TTS development team.
				Report temporal anomalies to the Gallifreyan High Council immediately upon detection

			COPYRIGHT
				Copyright (C) 10432 RE - TAMA Studios.
				Through Time and Space! is free software.
				There is NO WARRANTY, to the extent permitted by law.

			SEE ALSO
				Full documentation <https://tama-studios.github.io/Through-Time-and-Space/>
				or available locally via: The TARDIS Type 40 Manual - Licensed by
				Kasterborous Shipyards""";
}