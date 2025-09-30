/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.caps;

import com.code.tama.tts.server.capabilities.interfaces.IPlayerCap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class PlayerCapability implements IPlayerCap {
    String ViewedTARDIS = "";
    Player player;

    public PlayerCapability(Entity player) {
        this.player = (Player) player;
    }

    @Override
    public String GetViewingTARDIS() {
        return this.ViewedTARDIS;
    }

    @Override
    public void SetViewingTARDIS(String tardis) {
        this.ViewedTARDIS = tardis;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("viewed_tardis", this.ViewedTARDIS);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.ViewedTARDIS = nbt.getString("viewed_tardis");
    }
}
