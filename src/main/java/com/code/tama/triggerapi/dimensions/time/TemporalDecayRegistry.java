/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.dimensions.time;

import static com.code.tama.tts.TTSMod.MODID;

import java.util.HashMap;
import java.util.List;
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
 * The decay chain, now branching rather than a straight lookup.
 * <p>
 * A single-outcome chain (the original design) gives every block of a given
 * type the exact same fate: a whole wall of oak planks becomes a whole wall of
 * oak slabs, uniformly, which reads as "the top half vanished" rather than as a
 * building falling apart. Branching fixes this by picking <em>one of
 * several</em> outcomes per block -- including, deliberately, "no change this
 * step" as one of the options, so some sections of a wall stay intact next to
 * sections that have crumbled or rotted through.
 * <p>
 * Selection is deterministic (hashed from world seed + block position + step
 * index), not {@code Random}-backed, so the same wall decays the same way every
 * time the chunk is replayed -- it varies in space, not in time.
 */
public final class TemporalDecayRegistry {

	public static final TagKey<Block> FRAGILE = TagKey.create(net.minecraft.core.registries.Registries.BLOCK,
			new ResourceLocation(MODID, "fragile_in_future"));

	/** One possible fate for a decaying block, with a relative likelihood. */
	public record DecayOutcome(Block block, int weight) {
	}

	private static final Map<Block, List<DecayOutcome>> BRANCHES = new HashMap<>();
	private static final int MAX_CHAIN_WALK = 16;

	private static final Map<Block, Boolean> FRAGILE_CACHE = new java.util.IdentityHashMap<>();

	private TemporalDecayRegistry() {
	}

	private static DecayOutcome o(Block block, int weight) {
		return new DecayOutcome(block, weight);
	}

	private static void branch(Block from, DecayOutcome... outcomes) {
		BRANCHES.put(from, List.of(outcomes));
	}

	static {
		// --- stone family ---------------------------------------------------------
		// Every branch includes a "stays as-is" option so not every block in a wall
		// moves.
		branch(Blocks.STONE, o(Blocks.STONE, 40), o(Blocks.COBBLESTONE, 60));
		branch(Blocks.COBBLESTONE, o(Blocks.COBBLESTONE, 35), o(Blocks.MOSSY_COBBLESTONE, 45), o(Blocks.GRAVEL, 20));
		branch(Blocks.MOSSY_COBBLESTONE, o(Blocks.MOSSY_COBBLESTONE, 40), o(Blocks.GRAVEL, 45),
				o(Blocks.MOSS_BLOCK, 15));
		branch(Blocks.GRAVEL, o(Blocks.GRAVEL, 100));

		branch(Blocks.STONE_BRICKS, o(Blocks.STONE_BRICKS, 30), o(Blocks.CRACKED_STONE_BRICKS, 50),
				o(Blocks.MOSSY_STONE_BRICKS, 20));
		branch(Blocks.CRACKED_STONE_BRICKS, o(Blocks.CRACKED_STONE_BRICKS, 35), o(Blocks.MOSSY_STONE_BRICKS, 40),
				o(Blocks.GRAVEL, 25));
		branch(Blocks.MOSSY_STONE_BRICKS, o(Blocks.MOSSY_STONE_BRICKS, 60), o(Blocks.GRAVEL, 40));

		branch(Blocks.STONE_BRICK_STAIRS, o(Blocks.STONE_BRICK_STAIRS, 40), o(Blocks.MOSSY_STONE_BRICK_STAIRS, 60));
		branch(Blocks.STONE_BRICK_SLAB, o(Blocks.STONE_BRICK_SLAB, 40), o(Blocks.MOSSY_STONE_BRICK_SLAB, 60));
		branch(Blocks.COBBLESTONE_STAIRS, o(Blocks.COBBLESTONE_STAIRS, 40), o(Blocks.MOSSY_COBBLESTONE_STAIRS, 60));
		branch(Blocks.COBBLESTONE_SLAB, o(Blocks.COBBLESTONE_SLAB, 40), o(Blocks.MOSSY_COBBLESTONE_SLAB, 60));
		branch(Blocks.COBBLESTONE_WALL, o(Blocks.COBBLESTONE_WALL, 40), o(Blocks.MOSSY_COBBLESTONE_WALL, 60));

		// --- surface ----------------------------------------------------------------
		branch(Blocks.GRASS_BLOCK, o(Blocks.GRASS_BLOCK, 50), o(Blocks.DIRT, 35), o(Blocks.COARSE_DIRT, 15));
		branch(Blocks.DIRT, o(Blocks.DIRT, 55), o(Blocks.COARSE_DIRT, 30), o(Blocks.GRAVEL, 15));
		branch(Blocks.COARSE_DIRT, o(Blocks.COARSE_DIRT, 60), o(Blocks.GRAVEL, 40));
		branch(Blocks.PODZOL, o(Blocks.PODZOL, 55), o(Blocks.COARSE_DIRT, 45));
		branch(Blocks.SAND, o(Blocks.SAND, 60), o(Blocks.SANDSTONE, 40));
		branch(Blocks.SANDSTONE, o(Blocks.SANDSTONE, 70), o(Blocks.SAND, 30));

		// --- wood: this is the branch that fixes the "flat slab pancake" complaint --
		// Four distinct fates instead of one: some planks survive, some become slabs
		// (visually lower), some rot through to cobwebs, some are swallowed by
		// encroaching
		// moss/rubble, some are just gone. A wall built from this looks like a ruin,
		// not
		// a uniformly mown lawn.
		branch(Blocks.OAK_PLANKS, o(Blocks.OAK_PLANKS, 25), o(Blocks.OAK_SLAB, 40), o(Blocks.COBWEB, 10),
				o(Blocks.MOSSY_COBBLESTONE, 10), o(Blocks.AIR, 15));
		branch(Blocks.OAK_SLAB, o(Blocks.OAK_SLAB, 30), o(Blocks.AIR, 45), o(Blocks.COBBLESTONE, 15),
				o(Blocks.MOSSY_COBBLESTONE, 10));

		branch(Blocks.SPRUCE_PLANKS, o(Blocks.SPRUCE_PLANKS, 25), o(Blocks.SPRUCE_SLAB, 40), o(Blocks.COBWEB, 10),
				o(Blocks.MOSSY_COBBLESTONE, 10), o(Blocks.AIR, 15));
		branch(Blocks.SPRUCE_SLAB, o(Blocks.SPRUCE_SLAB, 30), o(Blocks.AIR, 45), o(Blocks.COBBLESTONE, 15),
				o(Blocks.MOSSY_COBBLESTONE, 10));

		branch(Blocks.OAK_LOG, o(Blocks.OAK_LOG, 30), o(Blocks.STRIPPED_OAK_LOG, 40), o(Blocks.AIR, 30));
		branch(Blocks.STRIPPED_OAK_LOG, o(Blocks.STRIPPED_OAK_LOG, 40), o(Blocks.AIR, 60));
		branch(Blocks.OAK_LEAVES, o(Blocks.AIR, 100));

		// --- metals
		// -------------------------------------------------------------------
		branch(Blocks.IRON_BLOCK, o(Blocks.IRON_BLOCK, 50), o(Blocks.RAW_IRON_BLOCK, 50));
		branch(Blocks.RAW_IRON_BLOCK, o(Blocks.RAW_IRON_BLOCK, 60), o(Blocks.AIR, 40));

		branch(Blocks.COPPER_BLOCK, o(Blocks.COPPER_BLOCK, 10), o(Blocks.EXPOSED_COPPER, 60),
				o(Blocks.WEATHERED_COPPER, 30));
		branch(Blocks.EXPOSED_COPPER, o(Blocks.EXPOSED_COPPER, 20), o(Blocks.WEATHERED_COPPER, 55),
				o(Blocks.OXIDIZED_COPPER, 25));
		branch(Blocks.WEATHERED_COPPER, o(Blocks.WEATHERED_COPPER, 40), o(Blocks.OXIDIZED_COPPER, 60));
		branch(Blocks.OXIDIZED_COPPER, o(Blocks.OXIDIZED_COPPER, 100));

		// --- glass
		// ----------------------------------------------------------------------
		branch(Blocks.GLASS, o(Blocks.GLASS, 40), o(Blocks.AIR, 60));
		branch(Blocks.GLASS_PANE, o(Blocks.GLASS_PANE, 40), o(Blocks.AIR, 60));
	}

	public static void registerBranch(Block from, DecayOutcome... outcomes) {
		branch(from, outcomes);
	}

	public static boolean hasChain(Block block) {
		return BRANCHES.containsKey(block);
	}

	/* ======================== Decay ======================== */

	/**
	 * Applies {@code steps} of branching decay to {@code state}, positioned at
	 * {@code pos}.
	 *
	 * @param dimSeed
	 *            the target dimension's seed, mixed into the per-block hash so
	 *            present and future decay differently rather than identically
	 * @param intensity
	 *            0..1 regional bias toward more severe outcomes. Pass the same
	 *            value used by {@link TemporalWeathering} at this column so that
	 *            structures decay harder in the same patches natural terrain does
	 *            -- a wall standing in an already-weathered region should look
	 *            worse than one in an untouched one.
	 */
	public static BlockState decay(BlockState state, int steps, BlockPos pos, long dimSeed, double intensity) {
		if (steps <= 0 || state.isAir())
			return state;
		if (isFragile(state))
			return Blocks.AIR.defaultBlockState();

		BlockState current = state;
		for (int i = 0; i < steps; i++) {
			List<DecayOutcome> outcomes = BRANCHES.get(current.getBlock());
			if (outcomes == null)
				break; // no rule: leave as-is
			if (outcomes.size() == 1 && outcomes.get(0).block() == current.getBlock())
				break; // terminal

			double sample = unitDouble(hash(dimSeed, pos.getX(), pos.getY(), pos.getZ(), i));
			// Skewing toward 1 as intensity rises biases the weighted pick toward outcomes
			// later in each branch's list -- by convention, later entries are more severe.
			double skewed = Math.pow(sample, 1.0 / (1.0 + intensity * 3.0));

			Block picked = pickWeighted(outcomes, skewed);
			if (picked == current.getBlock())
				break; // rolled "no change" -- stop here
			if (picked == Blocks.AIR)
				return Blocks.AIR.defaultBlockState();

			current = copyProperties(current, picked.defaultBlockState());
		}
		return current;
	}

	public static BlockState decayFully(BlockState state, BlockPos pos, long dimSeed, double intensity) {
		return decay(state, MAX_CHAIN_WALK, pos, dimSeed, intensity);
	}

	private static Block pickWeighted(List<DecayOutcome> outcomes, double skewedSample) {
		int total = 0;
		for (DecayOutcome out : outcomes)
			total += out.weight();

		double target = skewedSample * total;
		double cumulative = 0;
		for (DecayOutcome out : outcomes) {
			cumulative += out.weight();
			if (target <= cumulative)
				return out.block();
		}
		return outcomes.get(outcomes.size() - 1).block();
	}

	/** SplitMix64-style finalizer -- cheap, no allocation, deterministic. */
	private static long hash(long seed, int x, int y, int z, int salt) {
		long h = seed ^ ((long) x * 0x9E3779B97F4A7C15L) ^ ((long) y * 0xBF58476D1CE4E5B9L)
				^ ((long) z * 0x94D049BB133111EBL) ^ ((long) salt * 0xD6E8FEB86659FD93L);
		h ^= (h >>> 33);
		h *= 0xff51afd7ed558ccdL;
		h ^= (h >>> 33);
		h *= 0xc4ceb9fe1a85ec53L;
		h ^= (h >>> 33);
		return h;
	}

	private static double unitDouble(long hash) {
		return (hash >>> 11) * 0x1.0p-53;
	}

	/* ======================== Fragility ======================== */

	public static boolean isFragile(BlockState state) {
		return isFragile(state, EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
	}

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
			fragile = false;
		}

		FRAGILE_CACHE.put(state.getBlock(), fragile);
		return fragile;
	}

	public static void clearCache() {
		FRAGILE_CACHE.clear();
	}

	/* ======================== Property carry-over ======================== */

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