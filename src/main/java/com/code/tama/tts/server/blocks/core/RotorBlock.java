/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks.core;

import net.minecraft.world.level.block.GlassBlock;

public class RotorBlock extends GlassBlock {
    public RotorBlock(Properties p_49795_) {
        super(p_49795_.lightLevel(blockstate -> 15));
    }
}
