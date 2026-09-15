/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import javax.annotation.Nullable;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

/**
 * A one-directional causal chain of three levels.
 * <p>
 * Causality flows strictly {@code PAST -> PRESENT -> FUTURE}. There is
 * deliberately no accessor for "the level before this one" -- the only
 * traversal exposed is forward, so backwards propagation is not merely
 * discouraged but structurally unavailable.
 *
 * @param base
 *            the level the player actually built in; the source of truth
 * @param present
 *            {@code base} + 1 decay step
 * @param future
 *            {@code base} + 2 decay steps
 */
public record TimeLinkage(ResourceKey<Level> base, ResourceKey<Level> present, ResourceKey<Level> future) {

	public static final String PRESENT_SUFFIX = "_present";
	public static final String FUTURE_SUFFIX = "_future";

	/**
	 * Derives the standard linkage for a base level: {@code modid:name_present} /
	 * {@code modid:name_future}.
	 */
	public static TimeLinkage forBase(ResourceKey<Level> base) {
		ResourceLocation id = base.location();
		return new TimeLinkage(base, derive(id, PRESENT_SUFFIX), derive(id, FUTURE_SUFFIX));
	}

	private static ResourceKey<Level> derive(ResourceLocation base, String suffix) {
		return ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION,
				new ResourceLocation(base.getNamespace(), base.getPath() + suffix));
	}

	/**
	 * How many decay steps separate {@code base} from the given member of this
	 * linkage.
	 *
	 * @return 0 for the base itself, 1 for present, 2 for future, or {@code -1} if
	 *         the level is not part of this linkage at all.
	 */
	public int decayStepsTo(ResourceKey<Level> target) {
		if (base.equals(target))
			return 0;
		if (present.equals(target))
			return 1;
		if (future.equals(target))
			return 2;
		return -1;
	}

	/**
	 * @return true if the given level is the origin of this chain (and therefore
	 *         may emit changes).
	 */
	public boolean isSource(ResourceKey<Level> level) {
		return base.equals(level);
	}

	/**
	 * @return true if the given level is downstream (present/future) and therefore
	 *         receives changes but must never emit them.
	 */
	public boolean isDownstream(ResourceKey<Level> level) {
		return present.equals(level) || future.equals(level);
	}

	public boolean contains(ResourceKey<Level> level) {
		return decayStepsTo(level) >= 0;
	}

	/**
	 * The downstream targets, in ascending decay order. Never includes
	 * {@link #base()}.
	 */
	public ResourceKey<Level>[] downstreamTargets() {
		@SuppressWarnings("unchecked")
		ResourceKey<Level>[] out = new ResourceKey[]{present, future};
		return out;
	}

	@Nullable public ResourceKey<Level> targetForSteps(int steps) {
		return switch (steps) {
			case 0 -> base;
			case 1 -> present;
			case 2 -> future;
			default -> null;
		};
	}
}