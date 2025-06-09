/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncButtonAnimationSetPacketS2C;
import com.code.tama.tts.server.tileentities.AbstractConsoleTile;

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
        Networking.sendPacketToDimension(consoleTile.getLevel().dimension(), new SyncButtonAnimationSetPacketS2C(consoleTile.ControlAnimationMap, consoleTile.getBlockPos()));
        this.NeedsUpdate = false;
    }

    public abstract InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player);
    public abstract InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player);
    abstract String GetName();
    public abstract SoundEvent GetSuccessSound();
    public abstract SoundEvent GetFailSound();
}