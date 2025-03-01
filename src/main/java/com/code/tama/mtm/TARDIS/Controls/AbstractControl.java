package com.code.tama.mtm.TARDIS.Controls;

import com.code.tama.mtm.Capabilities.Interfaces.ITARDISLevel;
import com.code.tama.mtm.Networking.Networking;
import com.code.tama.mtm.Networking.Packets.Entities.SyncButtonAnimationSetPacket;
import com.code.tama.mtm.TileEntities.AbstractConsoleTile;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractControl {
    private float AnimationState = 0.0f;
    private boolean NeedsUpdate;
    public boolean NeedsUpdate() {
        return this.NeedsUpdate;
    }
    public void SetNeedsUpdate(boolean Update) {
        this.NeedsUpdate = Update;
    }
    public float GetAnimationState() {
        return this.AnimationState;
    }
    public void SetAnimationState(float state) {
        this.AnimationState = state;
        this.NeedsUpdate = true;
    }
    public void UpdateClient(AbstractConsoleTile consoleTile) {
        Networking.sendPacketToDimension(consoleTile.getLevel().dimension(), new SyncButtonAnimationSetPacket(consoleTile.ControlAnimationMap, consoleTile.getBlockPos()));
        this.NeedsUpdate = false;
    }
    public abstract InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player);
    public abstract InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player);
    abstract String GetName();
    public abstract SoundEvent GetSuccessSound();
    public abstract SoundEvent GetFailSound();
}
