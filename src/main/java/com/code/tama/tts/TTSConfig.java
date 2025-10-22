/* (C) TAMA Studios 2025 */
package com.code.tama.tts;

import net.minecraftforge.common.ForgeConfigSpec;

public class TTSConfig {
	public static class ClientConfig {
		public static ForgeConfigSpec.BooleanValue BOTI_ENABLED;

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
			BOTI_RENDER_DISTANCE = builder.defineInRange("boti_render_distance", 6, 0, 16);
			builder.comment("Is BOTI Enabled (values: true/false. Default false)");
			BOTI_ENABLED = builder.define("boti_enabled", false);

			builder.pop();
		}
	}

	public static class ServerConfig {
		public static ForgeConfigSpec.BooleanValue BOTI_ENABLED;

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
			BOTI_RENDER_DISTANCE = builder.defineInRange("boti_render_distance", 6, 0, 16);
			builder.comment("Is BOTI Enabled (Values: true/false. Default true)");
			BOTI_ENABLED = builder.define("boti_enabled", true);

			builder.pop();
		}
	}
}
