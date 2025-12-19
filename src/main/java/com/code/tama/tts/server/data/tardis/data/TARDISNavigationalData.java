/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis.data;

import com.code.tama.tts.server.capabilities.caps.TARDISLevelCapability;
import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
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
					ResourceKey.codec(Registries.DIMENSION).fieldOf("exteriorDimensionKey")
							.forGetter(TARDISNavigationalData::getExteriorDimensionKey),
					ResourceKey.codec(Registries.DIMENSION).fieldOf("destinationDimensionKey")
							.forGetter(TARDISNavigationalData::getDestinationDimensionKey),
					SpaceTimeCoordinate.CODEC.fieldOf("destination").forGetter(TARDISNavigationalData::getDestination),
					SpaceTimeCoordinate.CODEC.fieldOf("location").forGetter(TARDISNavigationalData::getLocation),
					Direction.CODEC.fieldOf("facing").forGetter(TARDISNavigationalData::getFacing),
					Direction.CODEC.fieldOf("destinationFacing")
							.forGetter(TARDISNavigationalData::getDestinationFacing))
			.apply(instance, TARDISNavigationalData::new));

	SpaceTimeCoordinate Destination = new SpaceTimeCoordinate(), Location = new SpaceTimeCoordinate();

	ResourceKey<Level> ExteriorDimensionKey = Level.OVERWORLD, DestinationDimensionKey = Level.OVERWORLD;

	Direction Facing = Direction.NORTH, DestinationFacing = Direction.NORTH;
	int Increment = 1;
	ITARDISLevel TARDIS;

	public TARDISNavigationalData(TARDISLevelCapability TARDIS) {
		this.TARDIS = TARDIS;
	}

	public TARDISNavigationalData(int Increment, ResourceKey<Level> exteriorDimensionKey,
			ResourceKey<Level> destinationDimensionKey, SpaceTimeCoordinate destination, SpaceTimeCoordinate location,
			Direction facing, Direction destinationFacing) {
		this.Increment = Increment;
		ExteriorDimensionKey = exteriorDimensionKey;
		DestinationDimensionKey = destinationDimensionKey;
		Destination = destination;
		Location = location;
		Facing = facing;
		DestinationFacing = destinationFacing;
	}

	public SpaceTimeCoordinate GetExteriorLocation() {
		return this.Location.copy();
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
		this.Location.setLevel(exteriorLevel);
		this.ExteriorDimensionKey = exteriorLevel;
	}

	public void SetExteriorLocation(SpaceTimeCoordinate loc) {
		this.Location = loc.copy();
	}

	public SpaceTimeCoordinate getDestination() {
		if (this.Destination.getLevel() == null)
			this.Destination.setLevel(this.DestinationDimensionKey);
		return this.Destination.copy();
	}

	public SpaceTimeCoordinate getLocation() {
		if (this.Location.getLevel() == null)
			this.Location.setLevel(this.ExteriorDimensionKey);
		return this.Location.copy();
	}

	public void setDestination(SpaceTimeCoordinate destination) {
		Destination = destination.copy();
	}

	public void setLocation(SpaceTimeCoordinate location) {
		Location = location.copy();
	}
}
