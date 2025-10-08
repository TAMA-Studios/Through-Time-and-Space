/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.interfaces;

import com.code.tama.tts.server.tardis.data.TARDISData;
import com.code.tama.tts.server.tardis.data.TARDISFlightData;
import com.code.tama.tts.server.tardis.data.TARDISNavigationalData;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public interface ITARDISLevel extends INBTSerializable<CompoundTag> {

    TARDISFlightData GetFlightData();

    TARDISNavigationalData GetNavigationalData();

    TARDISData GetData();

    void setData(TARDISData data);

    void setNavigationalData(TARDISNavigationalData data);

    void setFlightData(TARDISFlightData data);

    /** Returns whether the TARDIS is capable of flight in its current state **/
    boolean CanFly();

    /**
     * Crash's the TARDIS, basically explosion at the exterior, particles, maybe
     * some fire
     **/
    void Crash();

    /**
     * Initiates the TARDIS takeoff sequence, the sequence goes as follows:
     * <ol>
     * <li>Starts takeoff animation and waits for it to finish</li>
     * <li>Calls ITARDISLevel#Fly() which then
     * <ul>
     * <li>Runs Calculations
     * <ul>
     * <li>Distance to Destination</li>
     * <li>Ticks before the Destination is reached</li>
     * </ul>
     * </li>
     * <li>Finishes up flight
     * <ul>
     * <li>Force loads the exterior world</li>
     * <li>Utterly Destroys the exterior</li>
     * <li>Un-force loads the exterior world</li>
     * </ul>
     * </li>
     * </ul>
     * </li>
     * </ol>
     **/
    void Dematerialize();

    /**
     * Finishes up the takeoff sequence by doing calculations and removing the
     * exterior block
     * <li>Runs Calculations
     * <ul>
     * <li>Distance to Destination</li>
     * <li>Ticks before the Destination is reached</li>
     * </ul>
     * </li>
     * <li>Finishes up flight
     * <ul>
     * <li>Force loads the exterior world</li>
     * <li>Utterly Destroys the exterior</li>
     * <li>Un-force loads the exterior world</li>
     * </ul>
     * </li>
     * </ul>
     * </li>
     **/
    void Fly();

    ResourceKey<Level> GetCurrentLevel();

    ExteriorTile GetExteriorTile();

    void SetExteriorTile(ExteriorTile tile);

    Level GetLevel();

    float GetLightLevel();
    /** Finishes up the landing sequence **/
    void Land();

    /** Initiates the TARDIS Landing sequence **/
    void Rematerialize();

    long getTicks();

    /** Does what it says on the tin **/
    void Tick();

    void UpdateClient();
}
