/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.sonic;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;

public abstract class SonicMode {
    public abstract Item getIcon();

    public abstract void onUse(Player player, BlockState usedOn, BlockPos usedPos);
}
