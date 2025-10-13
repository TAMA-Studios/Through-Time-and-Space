/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.subsystems;

import java.util.Map;
import lombok.NoArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

@NoArgsConstructor
public class DynamorphicGeneratorStack extends AbstractSubsystem {
  @Override
  public Map<BlockPos, BlockState> BlockMap() {

    //
    // p p
    //
    // sds
    // cdc

    //  d
    //  d
    // pdp
    //  d
    // ddd

    //
    //
    //  p
    // sds
    // cdc

    return Map.of();
  }

  @Override
  public void OnActivate(Level level, BlockPos blockPos) {}

  @Override
  public void OnDeActivate(Level level, BlockPos blockPos) {}

  @Override
  public String name() {
    return "dynamorphic_generator";
  }
}
