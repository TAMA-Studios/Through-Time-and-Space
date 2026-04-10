/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.teleporting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TickScheduler {

	// CopyOnWriteArrayList so that tasks scheduled from inside a running task
	// (e.g. removeChunkTickets calling runAfter) don't cause
	// ConcurrentModificationException on the iterator.
	private static final List<ScheduledTask> TASKS = new CopyOnWriteArrayList<>();

	/**
	 * Call this once from your mod's constructor or FMLCommonSetupEvent:
	 * MinecraftForge.EVENT_BUS.register(TickScheduler.class);
	 *
	 * Without registration the @SubscribeEvent below never fires and scheduled
	 * tasks (including chunk ticket release) will silently never run.
	 */
	public static void register() {
		MinecraftForge.EVENT_BUS.register(TickScheduler.class);
	}

	public static void runAfter(int ticks, Runnable task) {
		TASKS.add(new ScheduledTask(ticks, task));
	}

	@SubscribeEvent
	public static void onServerTick(TickEvent.ServerTickEvent event) {
		if (event.phase != TickEvent.Phase.END)
			return;

		// Collect tasks that are ready to fire so we can remove them outside the loop.
		List<ScheduledTask> toRemove = new ArrayList<>();

		for (ScheduledTask task : TASKS) {
			task.ticksRemaining--;
			if (task.ticksRemaining <= 0) {
				try {
					task.runnable.run();
				} catch (Exception e) {
					// Don't let one bad task kill the whole scheduler
					System.err.println("[TickScheduler] Task threw: " + e.getMessage());
					e.printStackTrace();
				}
				toRemove.add(task);
			}
		}

		TASKS.removeAll(toRemove);
	}

	private static class ScheduledTask {
		int ticksRemaining;
		final Runnable runnable;

		ScheduledTask(int ticks, Runnable runnable) {
			this.ticksRemaining = ticks;
			this.runnable = runnable;
		}
	}
}