/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.tree;

import com.code.tama.tts.server.worlds.MConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GallifreyanOakTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(
            @NotNull RandomSource pRandom, boolean pHasFlowers) {
        return MConfiguredFeatures.GALLIFREYAN_OAK_KEY;
    }
}
