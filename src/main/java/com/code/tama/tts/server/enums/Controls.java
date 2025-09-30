/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.enums;

import com.code.tama.tts.server.tardis.controls.*;

public enum Controls {
    EMPTY(new EmptyControl()),
    ENVIRONMENT_SCANNER(new EnvironmentScannerControl()),
    FACING_CONTROL(new FacingControl()),
    INCREMENT_CONTROL(new IncrementControl()),
    POWER_CONTROL(new PowerControl()),
    THROTTLE(new ThrottleControl()),
    VARIANT_CONTROL(new VariantControl()),
    X_CONTROL(new X_Control()),
    Y_CONTROL(new Y_Control()),
    Z_CONTROL(new Z_Control()),
    ;
    private final AbstractControl control;

    Controls(AbstractControl control) {
        this.control = control;
    }

    public Controls Cycle() {
        if (this.ordinal() + 1 >= Controls.values().length) {
            return Controls.values()[0];
        } else return Controls.values()[this.ordinal() + 1];
    }

    public Controls CycleBackwards() {
        if (this.ordinal() - 1 <= 0) {
            return Controls.values()[Controls.values().length];
        } else return Controls.values()[this.ordinal() - 1];
    }

    public AbstractControl GetControl() {
        return this.control;
    }
}
