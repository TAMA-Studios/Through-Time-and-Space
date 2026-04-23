/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.events;

import com.code.tama.tts.server.data.RiftData;
import lombok.AllArgsConstructor;
import lombok.Getter;

import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.ListenerList;
import net.minecraftforge.eventbus.api.Event;

@AllArgsConstructor
public class RiftEvent extends Event {

	private static final ListenerList LISTENER_LIST = new ListenerList();

	@Override
	public ListenerList getListenerList() {
		return LISTENER_LIST;
	}

	@Getter
	public final Level level;
	@Getter
	public final RiftData rift;

	public static class NewRift extends RiftEvent {
		public NewRift(Level level, RiftData rift) {
			super(level, rift);
		}

		private static final ListenerList LISTENER_LIST = new ListenerList();

		@Override
		public ListenerList getListenerList() {
			return LISTENER_LIST;
		}
	}
}