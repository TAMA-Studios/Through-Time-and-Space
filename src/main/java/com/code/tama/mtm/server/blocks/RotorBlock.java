package com.code.tama.mtm.server.blocks;

import net.minecraft.world.level.block.GlassBlock;

public class RotorBlock extends GlassBlock {
    public RotorBlock(Properties p_49795_) {
        super(p_49795_.lightLevel(blockstate -> 15));
    }
}
