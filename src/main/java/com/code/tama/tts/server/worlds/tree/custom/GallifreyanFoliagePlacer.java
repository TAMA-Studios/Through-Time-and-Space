/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.tree.custom;

import com.code.tama.tts.server.worlds.tree.ModFoliagePlacers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

public class GallifreyanFoliagePlacer extends BlobFoliagePlacer {
	public static final Codec<GallifreyanFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> blobParts(instance).apply(instance, GallifreyanFoliagePlacer::new));

	public GallifreyanFoliagePlacer(IntProvider p_161397_, IntProvider p_161398_, int p_161399_) {
		super(p_161397_, p_161398_, p_161399_);
	}

	protected void createFoliage(@NotNull LevelSimulatedReader levelSimulatedReader, FoliagePlacer.@NotNull FoliageSetter foliageSetter, @NotNull RandomSource randomSource, @NotNull TreeConfiguration treeConfiguration, int p_225586_, FoliagePlacer.@NotNull FoliageAttachment foliageAttachment, int i2, int i3, int i1) {
		for(int i = i1; i >= i1 - i2; --i) {
			int j = i3 + (i != i1 && i != i1 - i2 ? 1 : 0);
			this.placeLeavesRow(levelSimulatedReader, foliageSetter, randomSource, treeConfiguration, foliageAttachment.pos(), j, i, foliageAttachment.doubleTrunk());
		}

	}

	protected boolean shouldSkipLocation(@NotNull RandomSource source, int i, int i1, int i2, int i3, boolean idfk) {
		return Mth.square((float)i + 0.5F) + Mth.square((float)i2 + 0.5F) > (float)(i3 * i3);
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return ModFoliagePlacers.GALLIFREYAN_OAK_PLACER.get();
	}
}
