package com.code.tama.mtm.TARDIS.Controls;

public class ControlAnimationStateHandler {
    float State;
    public long AnimTicksStarted;

    void SetState(float State) {
        this.State = State;
    }

    float GetState() {
        return this.State;
    }

    void AnimTick(long GameTime, long AnimLength, float NextState) {
        if(GameTime - this.AnimTicksStarted >= AnimLength) {
            this.State = NextState;
            return;
        }
    }
}
