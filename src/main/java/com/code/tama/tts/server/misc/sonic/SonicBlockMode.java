package com.code.tama.tts.server.misc.sonic;

import com.code.tama.tts.TTSMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public class SonicBlockMode extends SonicMode{
    public ResourceLocation getTexture() {
        return new ResourceLocation(TTSMod.MODID, "");
    }

    public void onUse(Player player, BlockState usedOn, BlockPos usedPos) {

    }
}
