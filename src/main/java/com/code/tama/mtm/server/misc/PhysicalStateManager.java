package com.code.tama.mtm.server.misc;

import com.code.tama.mtm.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.mtm.server.tileentities.ExteriorTile;

import java.text.DecimalFormat;

import static java.lang.Math.sin;

public class PhysicalStateManager {
    long TicksOld, Ticks, Difference, DifferenceOld;
    ITARDISLevel itardisLevel;
    ExteriorTile exteriorTile;
    boolean IsFading;
    float Alpha, AlphaModifierPositive, AlphaModifierNegative;
    int Stage, Cycles;
    double SineModifier = 0.05;

    public PhysicalStateManager(ITARDISLevel itardisLevel, ExteriorTile exteriorTile) {
        this.itardisLevel = itardisLevel;
        this.exteriorTile = exteriorTile;
    }

    public void TakeOff() {
        this.IsFading = true;
        this.Alpha = 1.0f;
        this.Ticks = exteriorTile.getLevel().getGameTime();
        this.TicksOld = this.Ticks;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        while(!itardisLevel.IsInFlight()) {
            // The difference between Ticks and TicksOld
            this.Difference = this.Ticks - this.TicksOld;

            // Only execute ON the tick, not like 5 billion times each tick
            if(this.Difference != this.DifferenceOld) {

                this.Alpha = Float.parseFloat(decimalFormat.format(this.AlphaModifierNegative + Math.abs((this.AlphaModifierPositive * sin(this.SineModifier * (this.Difference+(1*Math.PI)))))));
                if(this.Alpha <= this.AlphaModifierNegative) this.Cycles++;
                //Math.min(this.AlphaModifierPositive, (float) Math.abs(Math.sin(this.Difference * this.SineModifier)));
                exteriorTile.setTransparency(this.Alpha);

                switch (this.Cycles) {
                    case 0:
                        this.AlphaModifierPositive = 0.2f;
                        this.AlphaModifierNegative = 0.8f;
                        break;
                    case 1:
                        this.AlphaModifierPositive = 0.4f;
                        this.AlphaModifierNegative = 0.4f;
                        break;
                    case 2:
                        this.AlphaModifierPositive = 0.4f;
                        this.AlphaModifierNegative = 0.2f;
                        break;
                    case 3:
                        this.AlphaModifierPositive = 0.2f;
                        this.AlphaModifierNegative = 0.2f;
                        break;
                    case 4:
                        this.AlphaModifierPositive = 0.2f;
                        this.AlphaModifierNegative = 0.0f;
                        break;
                    default:
                        this.AlphaModifierPositive = 0.0f;
                }

                if (this.AlphaModifierPositive == 0.0f && this.Alpha <= 0.1f) {
                    if(this.itardisLevel.GetFlightScheme().GetTakeoff().IsFinished()) {
                        this.itardisLevel.Fly();
                        break;
                    }
                }
                this.DifferenceOld = this.Difference;
            }

            // Make sure the exterior is solid before changing timings
            if(this.Alpha >= 0.99f) {
//                this.Stage = this.Difference > 130 ? this.Difference >= 360 ? 2 : 1 : 0;
//                this.SineModifier = this.Difference > 130 ? 0.06 : 0.04;
                this.SineModifier = 0.08f;
            }

            // Make sure it doesn't go on for too long
            if(this.Cycles > 10) {
                if(this.Cycles > 30) {
                    this.itardisLevel.Fly();
                    break;
                }

                if(this.itardisLevel.GetFlightScheme().GetTakeoff().IsFinished()) {
                    this.itardisLevel.Fly();
                    break;
                }
            }

            if (this.Stage >= 2 && this.Alpha <= 0.1f) {
                if(this.itardisLevel.GetFlightScheme().GetTakeoff().IsFinished()) {
                    this.itardisLevel.Fly();
                    break;
                }
            }
            this.Ticks = exteriorTile.getLevel().getGameTime();
        }
    }

    public void Land() {
        this.IsFading = false;
        this.Alpha = 0;
        this.Ticks = exteriorTile.getLevel().getGameTime();
        this.TicksOld = this.Ticks;
        while(!itardisLevel.IsInFlight()) {

            this.Difference = this.Ticks = this.TicksOld;
            this.Ticks = exteriorTile.getLevel().getGameTime();
            if(this.Alpha == 20)
                this.IsFading = true;
            if(this.Alpha == 0)
                this.IsFading = false;

            if(this.IsFading) this.Alpha--;
            else this.Alpha++;

            this.exteriorTile.setTransparency(this.Alpha / 20);

            if(this.Difference >= 360 && this.Alpha == 20) {
                this.itardisLevel.Land();
                break;
            }
        }
    }

    public void Phase() {
        this.Ticks = exteriorTile.getLevel().getGameTime();
        this.IsFading = true;
        this.Alpha = 20;
        this.TicksOld = this.Ticks;
        while(!itardisLevel.IsInFlight()) {

            this.Difference = this.Ticks = this.TicksOld;
            this.Ticks = exteriorTile.getLevel().getGameTime();
            if(this.Alpha == 20)
                this.IsFading = true;
            if(this.Alpha == 0)
                this.IsFading = false;

            if(this.IsFading) this.Alpha--;
            else this.Alpha++;

            this.exteriorTile.setTransparency(this.Alpha / 20);

            if(this.Difference >= 40 && this.Alpha == 20) {
                break;
            }
        }
    }

    public static void Takeoff(ITARDISLevel itardisLevel, ExteriorTile exteriorTile) {
        new PhysicalStateManager(itardisLevel, exteriorTile).TakeOff();
    }

    public static void Land(ITARDISLevel itardisLevel, ExteriorTile exteriorTile) {
        new PhysicalStateManager(itardisLevel, exteriorTile).Land();
    }
}
