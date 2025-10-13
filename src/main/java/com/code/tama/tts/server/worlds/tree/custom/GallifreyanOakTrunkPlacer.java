/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.worlds.tree.custom;

import com.code.tama.tts.server.worlds.tree.ModTrunkPlacerTypes;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.NotNull;

public class GallifreyanOakTrunkPlacer extends TrunkPlacer {
  public static final Codec<GallifreyanOakTrunkPlacer> CODEC =
      RecordCodecBuilder.create(
          gallifreyanOakTrunkPlacerInstance ->
              trunkPlacerParts(gallifreyanOakTrunkPlacerInstance)
                  .apply(gallifreyanOakTrunkPlacerInstance, GallifreyanOakTrunkPlacer::new));

  public GallifreyanOakTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
    super(pBaseHeight, pHeightRandA, pHeightRandB);
  }

  @Override
  public @NotNull List<FoliagePlacer.FoliageAttachment> placeTrunk(
      @NotNull LevelSimulatedReader pLevel,
      @NotNull BiConsumer<BlockPos, BlockState> pBlockSetter,
      @NotNull RandomSource pRandom,
      int pFreeTreeHeight,
      BlockPos pPos,
      @NotNull TreeConfiguration pConfig) {
    // THIS IS WHERE BLOCK PLACING LOGIC GOES
    setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
    int height =
        pFreeTreeHeight
            + pRandom.nextInt(heightRandA, heightRandA + 3)
            + pRandom.nextInt(heightRandB - 1, heightRandB + 1);

    for (int i = 0; i < height; i++) {
      placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);

      int AmtToPlace =
          switch (i) {
            case 2 -> 5;
            case 4 -> 4;
            case 6 -> 3;
            case 8 -> 2;
            default -> 0;
          };

      if (i % 2 == 0 && pRandom.nextBoolean()) {
        if (pRandom.nextFloat() > 0.25f) {
          for (int x = 0; x < AmtToPlace; x++) {
            pBlockSetter.accept(
                pPos.above(i).relative(Direction.NORTH, x),
                ((BlockState)
                    Function.identity()
                        .apply(
                            pConfig
                                .trunkProvider
                                .getState(pRandom, pPos)
                                .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))));
          }
        }

        if (pRandom.nextFloat() > 0.25f) {
          for (int x = 0; x < AmtToPlace; x++) {
            pBlockSetter.accept(
                pPos.above(i).relative(Direction.SOUTH, x),
                ((BlockState)
                    Function.identity()
                        .apply(
                            pConfig
                                .trunkProvider
                                .getState(pRandom, pPos)
                                .setValue(RotatedPillarBlock.AXIS, Direction.Axis.Z))));
          }
        }

        if (pRandom.nextFloat() > 0.25f) {
          for (int x = 0; x < AmtToPlace; x++) {
            pBlockSetter.accept(
                pPos.above(i).relative(Direction.EAST, x),
                ((BlockState)
                    Function.identity()
                        .apply(
                            pConfig
                                .trunkProvider
                                .getState(pRandom, pPos)
                                .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X))));
          }
        }

        if (pRandom.nextFloat() > 0.25f) {
          for (int x = 0; x < AmtToPlace; x++) {
            pBlockSetter.accept(
                pPos.above(i).relative(Direction.WEST, x),
                ((BlockState)
                    Function.identity()
                        .apply(
                            pConfig
                                .trunkProvider
                                .getState(pRandom, pPos)
                                .setValue(RotatedPillarBlock.AXIS, Direction.Axis.X))));
          }
        }
      }
    }

    return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(height), 0, false));
  }

  @Override
  protected @NotNull TrunkPlacerType<?> type() {
    return ModTrunkPlacerTypes.GALLIFREYAN_OAK_TRUNK_PLACER.get();
  }
}
