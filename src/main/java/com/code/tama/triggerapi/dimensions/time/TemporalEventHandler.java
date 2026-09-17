/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import static com.code.tama.tts.TTSMod.MODID;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Wires the diff log to the world.
 * <p>
 * Two halves:
 * <ul>
 * <li><b>Capture</b> -- block edits in a <em>base</em> level are appended to
 * its diff log. Downstream levels are never captured from; that is the
 * enforcement point for one-directional causality.</li>
 * <li><b>Replay</b> -- on <em>every</em> downstream chunk load, the base's
 * edits are replayed into the chunk at the right decay depth. Weathering runs
 * alongside it, but only the first time that chunk is ever loaded.</li>
 * </ul>
 * The two have deliberately different lifecycles: weathering is destructive and
 * must happen once, replay is idempotent and must happen always. Collapsing
 * them into one "have I processed this chunk" guard is wrong in both
 * directions.
 */
@Mod.EventBusSubscriber(modid = MODID)
public final class TemporalEventHandler {

	private TemporalEventHandler() {
	}

	/* ======================== Capture ======================== */

	@SubscribeEvent
	public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
		if (!(event.getLevel() instanceof ServerLevel level))
			return;
		capture(level, event.getPos(), event.getPlacedBlock());
	}

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		if (!(event.getLevel() instanceof ServerLevel level))
			return;
		capture(level, event.getPos(), Blocks.AIR.defaultBlockState());
	}

	/**
	 * The hot path -- runs on every placement/break in every dimension, so the
	 * bail-out for unlinked levels is a single hash lookup with no allocation.
	 */
	private static void capture(ServerLevel level, BlockPos pos, BlockState newState) {
		MinecraftServer server = level.getServer();
		TimeLinkageRegistry registry = TimeLinkageRegistry.get(server);

		TimeLinkage linkage = registry.lookup(level.dimension());
		if (linkage == null)
			return; // not time-linked: nothing to do
		if (!linkage.isSource(level.dimension()))
			return; // downstream: never emits

		TemporalDiffLog log = TemporalDiffLog.get(level);
		log.record(level, pos, newState);

		// Live-apply to any downstream level whose target chunk happens to be loaded,
		// so
		// players standing in the future see changes ripple through immediately. If it
		// isn't
		// loaded, the diff log alone is enough -- replay will pick it up on load.
		for (var targetKey : linkage.downstreamTargets()) {
			ServerLevel target = server.getLevel(targetKey);
			if (target == null)
				continue; // never visited: stays lazy

			int steps = linkage.decayStepsTo(targetKey);
			if (!target.hasChunkAt(pos))
				continue;

			BlockPos projected = TimeMachine.projectPosition(level, target, pos);
			if (!target.hasChunkAt(projected))
				continue;

			if (TemporalDecayRegistry.isFragile(newState)) {
				target.setBlock(projected, Blocks.AIR.defaultBlockState(), 2);
				continue;
			}

			// Single-block sample, not a whole-chunk scan -- cheap enough to build on
			// demand
			// so this live edit uses the same intensity field the chunk's own weathering
			// pass
			// used, rather than assuming zero intensity.
			double intensity = new TemporalWeathering(target.getSeed(), steps, TimeTravelConfig.WEATHER_THRESHOLD,
					TimeTravelConfig.WEATHER_DEPTH).sampleIntensity(projected.getX(), projected.getZ());

			BlockState decayed = TemporalDecayRegistry.decay(newState, steps, projected, target.getSeed(), intensity);
			target.setBlock(projected, decayed, 2);
		}
	}

	/* ======================== Replay ======================== */

	@SubscribeEvent
	public static void onChunkLoad(ChunkEvent.Load event) {
		if (!(event.getLevel() instanceof ServerLevel level))
			return;

		MinecraftServer server = level.getServer();
		TimeLinkage linkage = TimeLinkageRegistry.get(server).lookup(level.dimension());
		if (linkage == null)
			return;

		int steps = linkage.decayStepsTo(level.dimension());
		if (steps <= 0)
			return; // base level: nothing to replay into

		ChunkAccess chunk = event.getChunk();
		ChunkPos pos = chunk.getPos();

		// --- Weathering: exactly once per chunk, ever.
		// ---------------------------------
		// Destructive and non-idempotent (it walks the decay chain), so the guard has
		// to be
		// persistent -- an in-memory set would re-weather everything after each restart
		// and
		// the world would erode a step further every time you loaded the save.
		TemporalWeathering weathering = new TemporalWeathering(level.getSeed(), steps,
				TimeTravelConfig.WEATHER_THRESHOLD, TimeTravelConfig.WEATHER_DEPTH);

		if (TemporalChunkState.get(level).markWeathered(pos)) {
			weathering.apply(level, chunk);

			// The future loses its fragile blocks entirely.
			if (steps >= 2) {
				TemporalWeathering.stripFragile(level, chunk);
			}
		}

		// --- Replay: every single load, unguarded.
		// ------------------------------------
		// Idempotent (fixed states from a log), and it MUST run every time: edits made
		// to the
		// base level while this chunk was unloaded are sitting in the diff log with no
		// other
		// way in. Guarding this behind an "already processed" flag is what silently
		// swallowed
		// edits to previously-visited chunks.
		//
		// Reuses `weathering` -- same intensity field the terrain pass just used, so a
		// house
		// standing in a heavily-weathered patch of ground decays harder than one that
		// isn't.
		ServerLevel base = server.getLevel(linkage.base());
		if (base != null) {
			TemporalDiffLog.get(base).replayInto(level, chunk, steps, weathering);
		}
	}

	@SubscribeEvent
	public static void onServerStopped(net.minecraftforge.event.server.ServerStoppedEvent event) {
		TemporalDecayRegistry.clearCache();
	}
}