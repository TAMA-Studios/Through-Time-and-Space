/* (C) TAMA Studios 2026 */
package com.code.tama.tts.core.achievements;

import lombok.Getter;

import net.minecraft.server.level.ServerPlayer;

public final class TTSAchievement {

	private TTSAchievement() {
	}

	public static void trigger(ServerPlayer player, String event) {
		TTSAchievementTriggers.TARDIS_EVENT.trigger(player, event);
	}

	public enum Achievements implements ImAnAchievement {
		CREATED_TARDIS("created_tardis"), FIRST_FLIGHT("first_flight"), FIRST_TAKEOFF("first_takeoff"),
		// ENTERED_VORTEX("entered_vortex"),
		BEHAVE("behave"), CRASH("crash"), FIRST_EVENT("first_event"), TARDIS_ENTRY("tardis_entry");

		Achievements(String id) {
			this.id = id;
		}

		@Getter
		private final String id;

		@Override
		public void trigger(ServerPlayer player) {
			TTSAchievementTriggers.TARDIS_EVENT.trigger(player, this.id);
		}
	}
}