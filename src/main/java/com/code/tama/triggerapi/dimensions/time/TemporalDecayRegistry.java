/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The decay chain: a directed mapping
 * {@code block -> what it becomes after one step of
 * aging}. Applying the chain N times is what produces "the future is doubly
 * decayed" without any special-cased future logic -- future simply runs the
 * same transform present runs, twice.
 * <p>
 * Chains terminate either at a fixed point (a block that maps to itself) or at
 * air.
 */
public final class TemporalDecayRegistry {

	/**
	 * Blocks in this tag are removed entirely in downstream dimensions rather than
	 * decayed. Data-driven so that mod-added flowers/torches/etc. get swept in
	 * without a code change.
	 */
	public static final TagKey<Block> FRAGILE = TagKey.create(net.minecraft.core.registries.Registries.BLOCK,
			new ResourceLocation(MODID, "fragile_in_future"));

	private static final Map<Block, Block> CHAIN = new HashMap<>();

	/** Guards against a malformed (cyclic) chain spinning forever. */
	private static final int MAX_CHAIN_WALK = 16;

	private TemporalDecayRegistry() {
	}

	static {
		// --- stone family -------------------------------------------------
		link(Blocks.STONE, Blocks.COBBLESTONE);
		link(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE);
		link(Blocks.MOSSY_COBBLESTONE, Blocks.GRAVEL);
		link(Blocks.GRAVEL, Blocks.GRAVEL); // terminal

		link(Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
		link(Blocks.CRACKED_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
		link(Blocks.MOSSY_STONE_BRICKS, Blocks.GRAVEL);

		link(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS);
		link(Blocks.STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB);
		link(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS);
		link(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB);
		link(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL);

		// --- surface ------------------------------------------------------
		link(Blocks.GRASS_BLOCK, Blocks.DIRT);
		link(Blocks.DIRT, Blocks.COARSE_DIRT);
		link(Blocks.COARSE_DIRT, Blocks.GRAVEL);
		link(Blocks.PODZOL, Blocks.COARSE_DIRT);
		link(Blocks.SAND, Blocks.SANDSTONE);
		link(Blocks.SANDSTONE, Blocks.SAND);

		// --- wood (rots away entirely) ------------------------------------
		link(Blocks.OAK_PLANKS, Blocks.OAK_SLAB);
		link(Blocks.OAK_SLAB, Blocks.AIR);
		link(Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_SLAB);
		link(Blocks.SPRUCE_SLAB, Blocks.AIR);
		link(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
		link(Blocks.STRIPPED_OAK_LOG, Blocks.AIR);
		link(Blocks.OAK_LEAVES, Blocks.AIR);

		// --- metals -------------------------------------------------------
		link(Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK);
		link(Blocks.RAW_IRON_BLOCK, Blocks.AIR);
		link(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER);
		link(Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER);
		link(Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
		link(Blocks.OXIDIZED_COPPER, Blocks.OXIDIZED_COPPER); // terminal

		// --- glass --------------------------------------------------------
		link(Blocks.GLASS, Blocks.AIR);
		link(Blocks.GLASS_PANE, Blocks.AIR);
	}

	public static void link(Block from, Block to) {
		CHAIN.put(from, to);
	}

	/**
	 * Applies the decay chain {@code steps} times.
	 * <p>
	 * Blockstate properties (stair facing, slab type, wall connections) are
	 * preserved where the target block shares them, so a mossy stair comes out
	 * facing the same way the clean stair did.
	 *
	 * @param state
	 *            the source state, as it exists in the base dimension
	 * @param steps
	 *            0 for the base itself, 1 for present, 2 for future
	 * @return the decayed state, possibly {@link Blocks#AIR}
	 */
	public static BlockState decay(BlockState state, int steps) {
		if (steps <= 0 || state.isAir())
			return state;

		// Fragile blocks don't gradually decay -- they simply aren't there any more.
		if (isFragile(state))
			return Blocks.AIR.defaultBlockState();

		BlockState current = state;
		for (int i = 0; i < steps; i++) {
			Block next = CHAIN.get(current.getBlock());
			if (next == null)
				break; // no rule: leave as-is
			if (next == current.getBlock())
				break; // terminal: stop walking
			if (next == Blocks.AIR)
				return Blocks.AIR.defaultBlockState();
			current = copyProperties(current, next.defaultBlockState());
		}
		return current;
	}

	/**
	 * Walks the chain to its end, for the weathering pass where "how worn" is
	 * driven by a noise field rather than a fixed step count.
	 */
	public static BlockState decayFully(BlockState state) {
		return decay(state, MAX_CHAIN_WALK);
	}

	/**
	 * Caches the collision-shape heuristic per block. Shape lookups are not free,
	 * and {@code stripFragile} asks this question up to 1024 times per chunk.
	 */
	private static final Map<Block, Boolean> FRAGILE_CACHE = new java.util.IdentityHashMap<>();

	/**
	 * A block is fragile if it is explicitly tagged, or -- as a fallback for
	 * untagged mod content -- if it has no collision shape and doesn't occlude.
	 * That catches torches, flowers, carpets, rails and the like automatically.
	 * <p>
	 * Convenience overload for callers with no position in hand (e.g. the diff log,
	 * which holds states but not yet a resolved target position).
	 */
	public static boolean isFragile(BlockState state) {
		return isFragile(state, EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
	}

	/**
	 * Position-aware form. Prefer this wherever a position is available.
	 * <p>
	 * A level and position are <strong>required</strong>, never null: blocks with
	 * an {@code offsetType} (grass, flowers, pointed dripstone) route
	 * {@code getShape} through {@code BlockState#getOffset}, whose offset function
	 * dereferences the position to derive its per-block jitter. Passing null there
	 * NPEs inside vanilla.
	 */
	public static boolean isFragile(BlockState state, BlockGetter level, BlockPos pos) {
		if (state.is(FRAGILE))
			return true;
		if (state.is(BlockTags.REPLACEABLE))
			return true;

		Boolean cached = FRAGILE_CACHE.get(state.getBlock());
		if (cached != null)
			return cached;

		boolean fragile;
		try {
			fragile = !state.canOcclude() && state.getCollisionShape(level, pos).isEmpty();
		} catch (Exception e) {
			// Some modded blocks assume a fully-populated level in getShape. A chunk
			// mid-load
			// isn't that, so treat an angry block as "not fragile" rather than taking down
			// the chunk task.
			fragile = false;
		}

		FRAGILE_CACHE.put(state.getBlock(), fragile);
		return fragile;
	}

	/** Clears the per-block heuristic cache. Call on server stop. */
	public static void clearCache() {
		FRAGILE_CACHE.clear();
	}

	/** Carries over any property the destination state also declares. */
	private static BlockState copyProperties(BlockState from, BlockState to) {
		BlockState result = to;
		for (net.minecraft.world.level.block.state.properties.Property<?> property : from.getProperties()) {
			if (result.hasProperty(property)) {
				result = copyProperty(from, result, property);
			}
		}
		return result;
	}

	private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to,
			net.minecraft.world.level.block.state.properties.Property<T> property) {
		return to.setValue(property, from.getValue(property));
	}
}