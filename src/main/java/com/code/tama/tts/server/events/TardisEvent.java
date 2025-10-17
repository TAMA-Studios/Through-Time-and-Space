/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.events;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

@AllArgsConstructor
public class TardisEvent extends Event {
	public final ITARDISLevel level;
	public final State state;

	public static class Crash extends TardisEvent {
		public Crash(ITARDISLevel level, State state) {
			super(level, state);
		}
	}

	public static class EntityEnterTARDIS extends TardisEvent {
		@Getter
		public final Entity entity;

		public EntityEnterTARDIS(ITARDISLevel level, State state, Entity entity) {
			super(level, state);
			this.entity = entity;
		}
	}

	public static class EntityExitTARDIS extends TardisEvent {
		@Getter
		public final Entity entity;

		public EntityExitTARDIS(ITARDISLevel level, State state, Entity entity) {
			super(level, state);
			this.entity = entity;
		}
	}

	public static class Land extends TardisEvent {
		public Land(ITARDISLevel level, State state) {
			super(level, state);
		}
	}

	/**
	 * Two params, START, and END, let's use takeoff as an example, START is called
	 * before any takeoff code begins, and END is called when all the takeoff code
	 * is finished. *
	 */
	public static enum State {
		END, START;
	}

	public static class TakeOff extends TardisEvent {
		public TakeOff(ITARDISLevel level, State state) {
			super(level, state);
		}
	}
}
