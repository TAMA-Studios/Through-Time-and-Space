/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.caps;

import com.code.tama.triggerapi.helpers.FileHelper;
import com.code.tama.tts.server.capabilities.interfaces.IPlayerCap;
import com.code.tama.tts.server.misc.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PlayerCapability implements IPlayerCap {
    String ViewedTARDIS = "";
    Player player;

    List<String> OwnedTARDISes = List.of();

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

    public void AddOwnedTARDIS(String UUID) {
        this.OwnedTARDISes.add(UUID);

        AtomicReference<String> Owned = new AtomicReference<>();
        this.OwnedTARDISes.forEach(tard -> Owned.set(Owned.get() + "\n" + tard));

        FileHelper.createStoredFile(
                "/pdat/" + player.getDisplayName().getString() + "-data",
                String.format(
                        """
                This file is to help server owners find info on players and owned TARDISes

                This player currently owns %s TARDISes

                Owned TARDISes:
                %s
                """,
                        this.OwnedTARDISes.size(), Owned.get()));
    }

    public int GetOwnedTARDISes() {
        return this.OwnedTARDISes.size();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("viewed_tardis", this.ViewedTARDIS);
        NBTUtils.putList("owned_tardises", this.OwnedTARDISes, tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.ViewedTARDIS = nbt.getString("viewed_tardis");
        this.OwnedTARDISes = NBTUtils.getList("owned_tardises", nbt);
    }
}
