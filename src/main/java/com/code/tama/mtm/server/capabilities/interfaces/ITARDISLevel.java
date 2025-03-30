package com.code.tama.mtm.server.capabilities.interfaces;

import com.code.tama.mtm.server.enums.tardis.FlightTerminationProtocolEnum;
import com.code.tama.mtm.server.tardis.flightsoundschemes.AbstractSoundScheme;
import com.code.tama.mtm.server.tileentities.ExteriorTile;
import com.code.tama.mtm.triggerapi.data.DoorData;
import com.code.tama.mtm.server.misc.ExteriorVariant;
import com.code.tama.mtm.server.misc.SpaceTimeCoordinate;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;

public interface ITARDISLevel extends INBTSerializable<CompoundTag> {
    SpaceTimeCoordinate GetDestination();
    void SetDestination(SpaceTimeCoordinate Destination);

    Level GetLevel();
    void SetPlayRotorAnimation(boolean PlayAnimation);
    boolean ShouldPlayRotorAnimation();
    void UpdateClient();

    void SetDestinationFacing(Direction Facing);
    void SetFacing(Direction Facing);
    Direction GetDestinationFacing();
    Direction GetFacing();
    public Direction NextDestinationFacing();

    ResourceKey<Level> GetCurrentLevel();
    void SetCurrentLevel(ResourceKey<Level> exteriorLevel);

    ExteriorTile GetExteriorTile();
    void SetExteriorTile(ExteriorTile exteriorTile);

    void SetExteriorLocation(SpaceTimeCoordinate blockPos);
    SpaceTimeCoordinate GetExteriorLocation();

    /** The position of the door block **/
    SpaceTimeCoordinate GetDoorBlock();
    /** The position of the door block **/
    void SetDoorBlock(SpaceTimeCoordinate doorBlock);

    /** The position of the door block **/
    DoorData GetDoorData();
    /** The position of the door block **/
    void SetDoorData(DoorData doorBlock);
    AbstractSoundScheme GetFlightScheme();
    void SetSoundScheme(AbstractSoundScheme SoundScheme);

    /** Whether the TARDIS is in flight**/
    boolean IsInFlight();
    void SetInFlight(boolean IsInFlight);

    /** Initiates the TARDIS takeoff sequence, the sequence goes as follows:
     * <ol>
     *     <li>Starts takeoff animation and waits for it to finish</li>
     *     <li>Calls ITARDISLevel#Fly() which then <ul>
     *     <li>Runs Calculations <ul>
     *         <li>Distance to Destination</li>
     *         <li>Ticks before the Destination is reached</li></ul></li>
     *         <li>Finishes up flight <ul>
     *             <li>Force loads the exterior world</li>
     *     <li>Utterly Destroys the exterior</li>
     *     <li>Un-force loads the exterior world</li>
     *     </ul></li>
     *     </ul></li>
     * </ol>**/
    void Dematerialize();

    /** Finishes up the takeoff sequence by doing calculations and removing the exterior block
     *  <li>Runs Calculations <ul>
     *      <li>Distance to Destination</li>
     *      <li>Ticks before the Destination is reached</li></ul></li>
     *  <li>Finishes up flight <ul>
     *      <li>Force loads the exterior world</li>
     *      <li>Utterly Destroys the exterior</li>
     *      <li>Un-force loads the exterior world</li>
     * </ul></li>
     * </ul></li>**/

    void Fly();
    /** Initiates the TARDIS Landing sequence**/
    void Rematerialize();
    /** Finishes up the landing sequence **/
    void Land();

    /** Crash's the TARDIS, basically explosion at the exterior, particles, maybe some fire **/
    void Crash();

    /** Gets the amount of ticks before the TARDIS reaches its destination **/
    int GetTicksTillReachedDestination();
    /** Gets the amount of ticks the TARDIS has been flying for (Not currently implemented TODO: Implement this) **/
    int GetTicksInFlight();

    /** Sets the amount of ticks before the TARDIS reaches its destination **/
    void SetTicksTillReachedDestination(int ticks);
    /** Sets the amount of ticks the TARDIS has been flying for (Not currently implemented TODO: Implement this) **/
    void SetTicksInFlight(int ticks);

    /** Sets whether the TARDIS is powered on or not **/
    void SetPowered(boolean IsPoweredOn);
    /** Gets whether the TARDIS is powered on or not **/
    boolean IsPoweredOn();

    int GetIncrement();
    void SetIncrement(int IncrementValue);
    int GetNextIncrement();
    int GetPreviousIncrement();

    FlightTerminationProtocolEnum GetFlightTerminationPolicy();
    void SetFlightTerminationPolicy(FlightTerminationProtocolEnum policy);

    ExteriorVariant GetExteriorVariant();
    void SetExteriorVariant(ExteriorVariant exteriorVariant);

    float GetLightLevel();
    void SetLightLevel(float LightLevel);
}
