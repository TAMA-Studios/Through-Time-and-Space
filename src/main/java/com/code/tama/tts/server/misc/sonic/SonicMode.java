package com.code.tama.tts.server.misc.sonic;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public abstract class SonicMode {
    public abstract ResourceLocation getTexture();

    public abstract void onUse(Player player, BlockState usedOn, BlockPos usedPos);
}
