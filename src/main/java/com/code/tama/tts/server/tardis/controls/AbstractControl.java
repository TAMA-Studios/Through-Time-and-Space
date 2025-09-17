/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis.controls;

import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.networking.Networking;
import com.code.tama.tts.server.networking.packets.S2C.entities.SyncButtonAnimationSetPacketS2C;
import com.code.tama.tts.server.tileentities.AbstractConsoleTile;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public abstract class AbstractControl {
    private float AnimationState = 0.0f;
    private boolean NeedsUpdate;

    public AnimationState usedState = new AnimationState();
    public long animationStartTime = Long.MAX_VALUE;

    public float GetAnimationState() {
        return this.AnimationState;
    }

    public abstract SoundEvent GetFailSound();

    public abstract SoundEvent GetSuccessSound();

    public boolean NeedsUpdate() {
        return this.NeedsUpdate;
    }

    public abstract InteractionResult OnLeftClick(ITARDISLevel itardisLevel, Entity player);

    public abstract InteractionResult OnRightClick(ITARDISLevel itardisLevel, Player player);

    public void SetAnimationState(float state) {
        this.AnimationState = state;
        this.NeedsUpdate = true;
    }

    public void SetNeedsUpdate(boolean Update) {
        this.NeedsUpdate = Update;
    }

    public void UpdateClient(AbstractConsoleTile consoleTile) {
        Networking.sendPacketToDimension(
                consoleTile.getLevel().dimension(),
                new SyncButtonAnimationSetPacketS2C(consoleTile.ControlAnimationMap, consoleTile.getBlockPos()));
        this.NeedsUpdate = false;
    }

    abstract String GetName();

    public AnimationState getUseAnimationState() {
        return this.usedState;
    }

    public void playAnimation(int time, boolean force) {
        if (time > this.animationStartTime + 20 || force) {
            this.usedState.start(time);
            this.animationStartTime = time;
        }
    }

    public void playAnimation(int time) {
        this.playAnimation(time, false);
    }

    public float getAnimationPercent(float time, int animationLength) {
        this.usedState.updateTime(time, 1.0F);
        float secondsElapsed = this.usedState.getAccumulatedTime() / 1000.0F;
        return Mth.clamp(secondsElapsed / (animationLength / 20.0F), 0, 1);
    }
}
