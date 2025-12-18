/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.events;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

@AllArgsConstructor
public class TardisEvent extends Event {
	@Getter
	public final ITARDISLevel level;

	public static class Crash extends TardisEvent {
		public final State state;
		public Crash(ITARDISLevel level, State state) {
			super(level);
			this.state = state;
		}
	}

	public static class FlightEventStart extends TardisEvent {
		public boolean isCancelable() {
			return true;
		}

		public FlightEventStart(ITARDISLevel level) {
			super(level);
		}
	}

	public static class FlightEventFail extends TardisEvent {
		public boolean isCancelable() {
			return true;
		}

		public FlightEventFail(ITARDISLevel level) {
			super(level);
		}
	}

	public static class FlightEventSucceed extends TardisEvent {
		public boolean isCancelable() {
			return true;
		}
		public FlightEventSucceed(ITARDISLevel level) {
			super(level);
		}
	}

	@Getter
	public static class EntityEnterTARDIS extends TardisEvent {
		public final Entity entity;
		public State state;

		public boolean isCancelable() {
			return this.state.equals(State.START);
		}
		public EntityEnterTARDIS(ITARDISLevel level, State state, Entity entity) {
			super(level);
			this.entity = entity;
			this.state = state;
		}
	}

	@Getter
	public static class EntityExitTARDIS extends TardisEvent {
		public final Entity entity;
		public State state;

		public boolean isCancelable() {
			return this.state.equals(State.START);
		}

		public EntityExitTARDIS(ITARDISLevel level, State state, Entity entity) {
			super(level);
			this.entity = entity;
			this.state = state;
		}
	}

	@Getter
	public static class Land extends TardisEvent {
		public State state;

		public boolean isCancelable() {
			return this.state.equals(State.START);
		}

		public Land(ITARDISLevel level, State state) {
			super(level);
			this.state = state;
		}
	}

	@Getter
	public static class TakeOff extends TardisEvent {
		public State state;

		public boolean isCancelable() {
			return this.state.equals(State.START);
		}

		public TakeOff(ITARDISLevel level, State state) {
			super(level);
			this.state = state;
		}
	}



	/**
	 * Two params, START, and END, let's use takeoff as an example, START is called
	 * before any takeoff code begins, and END is called when all the takeoff code
	 * is finished. *
	 */
	public enum State {
		END, START;
	}
}
