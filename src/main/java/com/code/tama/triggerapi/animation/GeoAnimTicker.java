/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.animation;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.Getter;

public class GeoAnimTicker {
	@Getter
	static volatile long ticks = 0;
	static ScheduledFuture<?> tick;
	private static final ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);

	public static void init() {
		tick = executor.scheduleAtFixedRate(GeoAnimTicker::tick, 0, 50, TimeUnit.MILLISECONDS);
	}

	public static void tick() {
		ticks++;
	}
}
