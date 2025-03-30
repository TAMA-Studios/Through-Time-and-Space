package com.code.tama.mtm.server.blocks;

import net.minecraft.world.level.block.Block;

public class RotorBlock extends Block {
    public RotorBlock(Properties p_49795_) {
        super(p_49795_.lightLevel(blockstate -> 15));
    }
}
