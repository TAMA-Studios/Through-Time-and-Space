/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis.data;

import com.code.tama.tts.core.misc.containers.NativeSpaceCoordinate;
import com.code.tama.tts.core.misc.containers.SpaceCoordinate;
import com.code.tama.tts.core.misc.containers.SpaceTimeCoordinate;
import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.Setter;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

@Getter
@Setter
public class TARDISNavigationalData {
	public static final Codec<TARDISNavigationalData> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Codec.INT.fieldOf("increment").forGetter(TARDISNavigationalData::getIncrement),
					SpaceTimeCoordinate.CODEC.fieldOf("destination")
							.forGetter(TARDISNavigationalData::GetDestinationSpacetimeCoord),
					SpaceTimeCoordinate.CODEC.fieldOf("location").forGetter(TARDISNavigationalData::getLocation),
					SpaceTimeCoordinate.CODEC
							.fieldOf("previous_location")
							.forGetter(TARDISNavigationalData::GetPreviousLocationSpaceTimeCoord),
					Direction.CODEC.fieldOf("facing").forGetter(TARDISNavigationalData::getFacing),
					Direction.CODEC.fieldOf("destinationFacing")
							.forGetter(TARDISNavigationalData::getDestinationFacing),
					ResourceKey.codec(Registries.DIMENSION).fieldOf("locDimensionKey")
							.forGetter(TARDISNavigationalData::getLocDimensionKey),
					ResourceKey.codec(Registries.DIMENSION).fieldOf("dest")
							.forGetter(TARDISNavigationalData::getDestDimensionKey))
			.apply(instance, TARDISNavigationalData::new));

	ResourceKey<Level> locDimensionKey = Level.OVERWORLD;
	ResourceKey<Level> destDimensionKey = Level.OVERWORLD;
	ResourceKey<Level> prevLocDimensionKey = Level.OVERWORLD;
	final long destAddr, locAddr, prevLocAddr;
	int locTimeZone, destTimeZone, prevLocTimezone;

	Direction Facing = Direction.NORTH, DestinationFacing = Direction.NORTH;
	int Increment = 1;
	ITARDISLevel TARDIS;

	public TARDISNavigationalData(TARDISLevelCapability TARDIS) {
		this.TARDIS = TARDIS;

		destAddr = NativeSpaceCoordinate.create();
		locAddr = NativeSpaceCoordinate.create();
		prevLocAddr = NativeSpaceCoordinate.create();
	}

	public TARDISNavigationalData(int Increment, SpaceTimeCoordinate destination, SpaceTimeCoordinate location,
			SpaceTimeCoordinate previousLocation, Direction facing, Direction destinationFacing,
			ResourceKey<Level> locDimensionKey, ResourceKey<Level> destDimensionKey) {
		this.Increment = Increment;
		this.locDimensionKey = locDimensionKey;
		Facing = facing;
		DestinationFacing = destinationFacing;

		destAddr = NativeSpaceCoordinate.create();
		locAddr = NativeSpaceCoordinate.create();
		prevLocAddr = NativeSpaceCoordinate.create();

		this.forceSetDestination(destination);
		this.setLocation(location);
		this.setPreviousLocation(previousLocation);
	}

	@Deprecated(forRemoval = true)
	public SpaceTimeCoordinate GetExteriorSpaceTimeCoord() {
		return new SpaceTimeCoordinate(NativeSpaceCoordinate.getX(locAddr), NativeSpaceCoordinate.getY(locAddr),
				NativeSpaceCoordinate.getZ(locAddr), locTimeZone, locDimensionKey);
	}

	@Deprecated(forRemoval = true)
	public SpaceTimeCoordinate GetPreviousLocationSpaceTimeCoord() {
		return new SpaceTimeCoordinate(NativeSpaceCoordinate.getX(locAddr), NativeSpaceCoordinate.getY(locAddr),
				NativeSpaceCoordinate.getZ(locAddr), locTimeZone, locDimensionKey);
	}

	public void SetExteriorLocation(SpaceTimeCoordinate loc) {
		SpaceCoordinate.memSet(this.locAddr, loc);
	}

	public int GetNextIncrement() {
		return switch (this.Increment) {
			case 1 -> 10;
			case 10 -> 100;
			case 100 -> 1000;
			case 1000 -> 10000;
			case 10000 -> 100000;
			default -> 1;
		};
	}

	public int GetPreviousIncrement() {
		return switch (this.Increment) {
			case 100000 -> 10000;
			case 10000 -> 1000;
			case 1000 -> 100;
			case 100 -> 10;
			case 10 -> 1;
			default -> 100000;
		};
	}

	public Direction NextDestinationFacing() {
		return this.DestinationFacing.getClockWise();
	}

	public void SetCurrentLevel(ResourceKey<Level> exteriorLevel) {
		this.locDimensionKey = exteriorLevel;
	}

	@Deprecated(forRemoval = true)
	public SpaceTimeCoordinate GetDestinationSpacetimeCoord() {
		return new SpaceTimeCoordinate(NativeSpaceCoordinate.getX(destAddr), NativeSpaceCoordinate.getY(destAddr),
				NativeSpaceCoordinate.getZ(destAddr), destTimeZone, destDimensionKey);
	}

	@Deprecated(forRemoval = true)
	public SpaceTimeCoordinate getLocation() {
		return GetExteriorSpaceTimeCoord();
	}

	/**
	 * Sets the TARDIS Destination IF the coordinate lock is NOT on
	 */
	@Deprecated(forRemoval = true)
	public void setDestination(SpaceTimeCoordinate destination) {
		if (this.TARDIS != null && !this.TARDIS.GetData().getControlData().isCoordinateLock())
			forceSetDestination(destination);
	}

	/** Sets the TARDIS Destination, ignoring the coordinate lock **/
	@Deprecated(forRemoval = true)
	public void forceSetDestination(SpaceTimeCoordinate destination) {
		SpaceCoordinate.memSet(this.destAddr, destination);
		this.destTimeZone = destination.getTimeZone();
		this.destDimensionKey = destination.getLevelKey();
	}

	@Deprecated(forRemoval = true)
	public void setLocation(SpaceTimeCoordinate location) {
		SpaceCoordinate.memSet(this.locAddr, location);
		this.locTimeZone = location.getTimeZone();
		this.locDimensionKey = location.getLevelKey();
	}

	@Deprecated(forRemoval = true)
	public void setPreviousLocation(SpaceTimeCoordinate location) {
		SpaceCoordinate.memSet(this.prevLocAddr, location);
		this.prevLocTimezone = location.getTimeZone();
		this.prevLocDimensionKey = location.getLevelKey();
	}
}
