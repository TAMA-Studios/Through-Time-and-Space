/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.interfaces;

import com.code.tama.tts.server.data.tardis.DoorData;
import com.code.tama.tts.server.enums.tardis.FlightTerminationProtocolEnum;
import com.code.tama.tts.server.misc.Exterior;
import com.code.tama.tts.server.misc.SpaceTimeCoordinate;
import com.code.tama.tts.server.tardis.data.SubsystemsData;
import com.code.tama.tts.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public interface ITARDISLevel extends INBTSerializable<CompoundTag> {
    public Direction NextDestinationFacing();
    /**
     * Crash's the TARDIS, basically explosion at the exterior, particles, maybe
     * some fire
     **/
    void Crash();

    void CycleVariant();
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

    SpaceTimeCoordinate GetDestination();

    Direction GetDestinationFacing();

    /** The position of the door block **/
    SpaceTimeCoordinate GetDoorBlock();
    /** The position of the door block **/
    DoorData GetDoorData();

    SpaceTimeCoordinate GetExteriorLocation();

    Exterior GetExteriorModel();

    ExteriorTile GetExteriorTile();

    Exterior GetExteriorVariant();

    Direction GetFacing();

    AbstractSoundScheme GetFlightScheme();

    FlightTerminationProtocolEnum GetFlightTerminationPolicy();

    int GetIncrement();

    Level GetLevel();

    float GetLightLevel();

    int GetNextIncrement();

    int GetPreviousIncrement();

    SubsystemsData GetSubsystemsData();
    /**
     * Gets the amount of ticks the TARDIS has been flying for (Not currently
     * implemented TODO: Implement this)
     **/
    int GetTicksInFlight();
    /** Gets the amount of ticks before the TARDIS reaches its destination **/
    int GetTicksTillReachedDestination();

    /** Whether the TARDIS is in flight **/
    boolean IsInFlight();
    /** Gets whether the TARDIS is powered on or not **/
    boolean IsPoweredOn();

    /** Finishes up the landing sequence **/
    void Land();

    /** Initiates the TARDIS Landing sequence **/
    void Rematerialize();

    void SetCurrentLevel(ResourceKey<Level> exteriorLevel);

    void SetDestination(SpaceTimeCoordinate Destination);

    void SetDestinationFacing(Direction Facing);

    /** The position of the door block **/
    void SetDoorBlock(SpaceTimeCoordinate doorBlock);
    /** The position of the door block **/
    void SetDoorData(DoorData doorBlock);

    void SetExteriorLocation(SpaceTimeCoordinate blockPos);

    void SetExteriorModel(Exterior Exterior);

    void SetExteriorTile(ExteriorTile exteriorTile);

    void SetExteriorVariant(Exterior exteriorVariant);

    void SetFacing(Direction Facing);

    void SetFlightTerminationPolicy(FlightTerminationProtocolEnum policy);

    void SetInFlight(boolean IsInFlight);

    void SetIncrement(int IncrementValue);

    void SetLightLevel(float LightLevel);

    void SetPlayRotorAnimation(boolean PlayAnimation);

    /** Sets whether the TARDIS is powered on or not **/
    void SetPowered(boolean IsPoweredOn);

    void SetSoundScheme(AbstractSoundScheme SoundScheme);
    /**
     * Sets the amount of ticks the TARDIS has been flying for (Not currently
     * implemented TODO: Implement this)
     **/
    void SetTicksInFlight(int ticks);
    /** Sets the amount of ticks before the TARDIS reaches its destination **/
    void SetTicksTillReachedDestination(int ticks);

    boolean ShouldPlayRotorAnimation();
    /** Does what it says on the tin **/
    void Tick();

    void UpdateClient();
}
