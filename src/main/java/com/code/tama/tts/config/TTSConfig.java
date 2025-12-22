/* (C) TAMA Studios 2025 */
package com.code.tama.tts.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class TTSConfig {
	public static class ClientConfig {
		public static ForgeConfigSpec.BooleanValue BOTI_ENABLED;
		public static ForgeConfigSpec.EnumValue<FlightType> FLIGHT_TYPE;

		public static ForgeConfigSpec.IntValue BOTI_RENDER_DISTANCE;

		public static final ForgeConfigSpec SPEC;
		static {
			ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
			setupConfig(configBuilder);
			SPEC = configBuilder.build();
		}

		private static void setupConfig(ForgeConfigSpec.Builder builder) {
			builder.comment("Through Time and Space - A Config");
			builder.push("BOTI Related Configs");

			builder.comment("BOTI Render Distance (Default 6)");
			BOTI_RENDER_DISTANCE = builder.defineInRange("boti_render_distance", 6, 0, 64);
			builder.comment("Is BOTI Enabled (values: true/false. Default false)");
			BOTI_ENABLED = builder.define("boti_enabled", false);

			builder.pop();
			builder.push("Flight Configuration");
			builder.comment("Flight Type (basic, normal, advanced)");
			FLIGHT_TYPE = builder.defineEnum("flight_type", FlightType.NORMAL);
			builder.pop();
		}
	}

	public static class ServerConfig {
		public static ForgeConfigSpec.BooleanValue BOTI_ENABLED;

		public static ForgeConfigSpec.IntValue BOTI_RENDER_DISTANCE;

		public static ForgeConfigSpec.IntValue TICKS_BETWEEN_FLIGHT_EVENT;
		public static ForgeConfigSpec.IntValue FLIGHT_EVENT_DURATION;

		public static ForgeConfigSpec.DoubleValue BLOCKS_PER_TICK;

		public static final ForgeConfigSpec SPEC;
		static {
			ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
			setupConfig(configBuilder);
			SPEC = configBuilder.build();
		}

		private static void setupConfig(ForgeConfigSpec.Builder builder) {
			builder.comment("	Through Time and Space - A Config");
			builder.comment("""
					We trust you have received the usual lecture from the local System Administrator.

					It usually boils down to these three things:
					#1) Respect the privacy of others.
					#2) Think before you type.
					#3) With great power comes great responsibility.""");
			builder.comment(
					"Important note: Time is measured in ticks, one tick is 1/20th of a second, meaning there are 20 ticks in one second.");
			builder.push("BOTI Config Values");

			builder.comment("BOTI Render Distance (Default 6)");
			BOTI_RENDER_DISTANCE = builder.defineInRange("boti_render_distance", 6, 0, 64);
			builder.comment("Is BOTI Enabled (Values: true/false. Default true)");
			BOTI_ENABLED = builder.define("boti_enabled", true);

			builder.pop();

			builder.push("Flight Config Values");
			builder.comment("The amount of ticks in between the end of a Flight Event and the start of a new one");
			TICKS_BETWEEN_FLIGHT_EVENT = builder.defineInRange("ticks_between_flight_events", 80, 0, Integer.MAX_VALUE);
			builder.comment("The default length for a flight event");
			FLIGHT_EVENT_DURATION = builder.defineInRange("flight_event_duration", 60, 0, Integer.MAX_VALUE);
			builder.comment("The default speed at which the TARDIS flies (measured in blocks per tick)");
			BLOCKS_PER_TICK = builder.defineInRange("blocks_per_tick", 0.1, 0, Double.MAX_VALUE);

			builder.pop();
		}
	}
}
