/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.capabilities.interfaces;

import com.code.tama.tts.server.data.tardis.data.*;
import com.code.tama.tts.server.tardis.ExteriorState;
import com.code.tama.tts.server.tardis.flight_events.AbstractFlightEvent;
import com.code.tama.tts.server.tileentities.ExteriorTile;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.ApiStatus;

public interface ITARDISLevel extends INBTSerializable<CompoundTag> {

	/** Returns whether the TARDIS is capable of flight in its current state */
	boolean CanFly();

	/**
	 * Crash's the TARDIS, basically explosion at the exterior, particles, maybe
	 * some fire
	 */
	void Crash();

	/**
	 * Initiates the TARDIS takeoff sequence, the sequence goes as follows:
	 *
	 * <ol>
	 * <li>Starts takeoff animation and waits for it to finish
	 * <li>Calls ITARDISLevel#Fly() which then
	 * <ul>
	 * <li>Runs Calculations
	 * <ul>
	 * <li>Distance to Destination
	 * <li>Ticks before the Destination is reached
	 * </ul>
	 * <li>Finishes up flight
	 * <ul>
	 * <li>Force loads the exterior world
	 * <li>Utterly Destroys the exterior
	 * <li>Un-force loads the exterior world
	 * </ul>
	 * </ul>
	 * </ol>
	 */
	void Dematerialize();

	/**
	 * Finishes up the takeoff sequence by doing calculations and removing the
	 * exterior block
	 * <li>Runs Calculations
	 *
	 * <ul>
	 * <li>Distance to Destination
	 * <li>Ticks before the Destination is reached
	 * </ul>
	 *
	 * <li>Finishes up flight
	 *
	 * <ul>
	 * <li>Force loads the exterior world
	 * <li>Utterly Destroys the exterior
	 * <li>Un-force loads the exterior world
	 * </ul>
	 *
	 * </ul>
	 */
	@ApiStatus.Internal
	void Fly();

	void ForceLoadExteriorChunk(boolean ForceLoad);

	/**
	 * THIS ONLY EXISTS CLIENT SIDE DO NOT REFERENCE IT FROM SERVERS OTHERWISE YOU
	 * ARE GAY AND WILL CRASH EVERYTHING
	 */
	@OnlyIn(Dist.CLIENT)
	TARDISClientData GetClientData();

	ResourceKey<Level> GetCurrentLevel();

	TARDISData GetData();

	TARDISEnvironmentalData GetEnvironmentalData();

	ExteriorTile GetExteriorTile();

	TARDISFlightData GetFlightData();

	Level GetLevel();

	float GetLightLevel();

	TARDISNavigationalData GetNavigationalData();

	/** Finishes up the landing sequence * */
	@ApiStatus.Internal
	void Land();

	/** Initiates the TARDIS Landing sequence * */
	void Rematerialize();

	void SetExteriorTile(ExteriorTile tile);

	/** Does what it says on the tin * */
	void Tick();

	void UpdateClient(int toUpdate);

	long getTicks();

	void setData(TARDISData data);

	void setFlightData(TARDISFlightData data);

	void setNavigationalData(TARDISNavigationalData data);

	void setCurrentFlightEvent(AbstractFlightEvent event);
	AbstractFlightEvent getCurrentFlightEvent();

	void UpdateExteriorState(ExteriorState state);
}
