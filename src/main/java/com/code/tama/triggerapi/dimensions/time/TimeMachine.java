/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;

import com.code.tama.triggerapi.dimensions.DimensionAPI;

/**
 * Public entry point for time travel.
 * <p>
 * Causality is one-directional by construction: these methods only ever move
 * <em>forward</em> along a {@link TimeLinkage}, and no downstream level ever
 * gets a block-change listener attached (see {@code TemporalEventHandler}), so
 * a future edit has nowhere to propagate to even in principle.
 */
public final class TimeMachine {

	private TimeMachine() {
	}

	/* ======================== Travel ======================== */

	/**
	 * Gets (creating if needed) the "future" counterpart of {@code baseLevel} --
	 * terrain aged by the erosion bias, weathered, fragile blocks gone, and every
	 * recorded player edit replayed at two decay steps.
	 */
	public static ServerLevel toTheFuture(ServerLevel baseLevel) {
		return getOrCreateDownstream(baseLevel, 2);
	}

	/** As {@link #toTheFuture}, but one decay step. */
	public static ServerLevel toThePresent(ServerLevel baseLevel) {
		return getOrCreateDownstream(baseLevel, 1);
	}

	/**
	 * Core creation path.
	 *
	 * @param baseLevel
	 *            the source of truth -- must not itself be a downstream level
	 * @param steps
	 *            1 for present, 2 for future
	 */
	public static ServerLevel getOrCreateDownstream(ServerLevel baseLevel, int steps) {
		MinecraftServer server = baseLevel.getServer();
		TimeLinkageRegistry registry = TimeLinkageRegistry.get(server);

		// Refuse to chain off a downstream level. Travelling "to the future of the
		// future"
		// would need a 3-step decay and a second diff log; not supported, and silently
		// allowing it would produce a level whose edits propagate nowhere.
		TimeLinkage existing = registry.lookup(baseLevel.dimension());
		if (existing != null && existing.isDownstream(baseLevel.dimension())) {
			throw new IllegalArgumentException(
					"Cannot create a downstream level from " + baseLevel.dimension().location()
							+ " -- it is already a downstream level of " + existing.base().location());
		}

		TimeLinkage linkage = existing != null ? existing : TimeLinkage.forBase(baseLevel.dimension());
		if (existing == null)
			registry.register(linkage);

		java.util.Objects.requireNonNull(linkage.targetForSteps(steps), "invalid decay step: " + steps);
		var targetKey = linkage.targetForSteps(steps);

		return DimensionAPI.get().getOrCreateLevel(server, targetKey,
				() -> AgedGenerator.createAgedStem(server, baseLevel, TimeTravelConfig.erosionBias(steps)));
	}

	/* ======================== Player transport ======================== */

	/**
	 * Sends a player forward along the chain, landing them on the target's surface
	 * at the same X/Z. The target's terrain has shifted, so their old Y is not
	 * reusable.
	 */
	public static void travel(ServerPlayer player, int steps) {
		ServerLevel from = player.serverLevel();
		MinecraftServer server = from.getServer();

		// Resolve the base of the chain: if the player is already downstream, they
		// travel
		// relative to the original, not relative to where they are standing.
		TimeLinkage linkage = TimeLinkageRegistry.get(server).lookup(from.dimension());
		ServerLevel base = linkage == null ? from : server.getLevel(linkage.base());
		if (base == null)
			base = from;

		ServerLevel target = steps == 0 ? base : getOrCreateDownstream(base, steps);

		int x = player.getBlockX();
		int z = player.getBlockZ();

		// Force the destination chunk so the heightmap is real before we read it --
		// otherwise
		// the player lands at whatever the empty chunk reports.
		target.getChunk(x >> 4, z >> 4);
		int y = target.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);

		player.teleportTo(target, x + 0.5, y, z + 0.5, player.getYRot(), player.getXRot());
	}

	/* ======================== Queries ======================== */

	/**
	 * Cheap "is this level time-linked" check for hot paths. Never creates a
	 * dimension.
	 */
	@Nullable public static TimeLinkage linkageOf(MinecraftServer server, net.minecraft.resources.ResourceKey<Level> level) {
		return TimeLinkageRegistry.get(server).lookup(level);
	}

	/** Where would {@code pos} in the base level end up, in the target level? */
	public static BlockPos projectPosition(ServerLevel base, ServerLevel target, BlockPos pos) {
		int sourceGround = base.getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
		BlockChangeRecord record = BlockChangeRecord.of(pos, base.getBlockState(pos), sourceGround);

		int targetGround = record.needsGroundLookup()
				? target.getChunkSource().getGenerator().getBaseHeight(pos.getX(), pos.getZ(),
						Heightmap.Types.WORLD_SURFACE, target, target.getChunkSource().randomState())
				: 0;

		return new BlockPos(pos.getX(), record.resolveY(targetGround), pos.getZ());
	}
}