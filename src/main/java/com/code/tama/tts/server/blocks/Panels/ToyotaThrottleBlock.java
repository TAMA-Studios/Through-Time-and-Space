/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.Panels;

import java.util.stream.Stream;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ToyotaThrottleBlock extends ThrottleBlock {
  public ToyotaThrottleBlock(Properties p_54120_) {
    super(p_54120_);
  }

  @Override
  public VoxelShape createShapeOFF() {
    return Stream.of(
            Block.box(0, 0, 0, 16, 1, 16),
            Block.box(4, 1, 3, 12, 2, 11),
            Block.box(4, 2, 4, 12, 3, 10),
            Block.box(4, 3, 5, 12, 4, 9),
            Block.box(4, 4, 8, 12, 5, 10),
            Block.box(4, 5, 9, 12, 6, 11),
            Block.box(4, 6, 10, 12, 7, 12),
            Block.box(4, 7, 11.2, 12, 8, 12.2))
        .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
        .get();
  }

  @Override
  public VoxelShape createShapeON() {
    return Stream.of(
            Block.box(0, 0, 0, 16, 1, 16),
            Block.box(4, 1, 3, 12, 2, 11),
            Block.box(4, 2, 4, 12, 3, 10),
            Block.box(4, 3, 5, 12, 4, 9),
            Block.box(4, 3, 2, 12, 4, 5),
            Block.box(4, 4, 0.5999999999999999, 12, 5, 3.5999999999999996),
            Block.box(4, 5, 0.5999999999999996, 12, 6, 1.5999999999999996))
        .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
        .get();
  }
}
