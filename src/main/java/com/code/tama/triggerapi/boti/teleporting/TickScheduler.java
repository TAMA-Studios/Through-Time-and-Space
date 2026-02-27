/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.boti.teleporting;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TickScheduler {

	private static final List<ScheduledTask> TASKS = new ArrayList<>();

	/**
	 * Call this once from your mod's constructor or FMLCommonSetupEvent:
	 *   MinecraftForge.EVENT_BUS.register(TickScheduler.class);
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

		Iterator<ScheduledTask> iter = TASKS.iterator();
		while (iter.hasNext()) {
			ScheduledTask task = iter.next();
			task.ticksRemaining--;
			if (task.ticksRemaining <= 0) {
				task.runnable.run();
				iter.remove();
			}
		}
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