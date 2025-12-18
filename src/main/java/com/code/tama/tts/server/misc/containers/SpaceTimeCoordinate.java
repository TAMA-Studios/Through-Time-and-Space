/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.containers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * Holds four doubles, X, Y, Z, and Time Can be NBT serialized/deserialized
 * (unlike {@link net.minecraft.core.BlockPos}) Can be created using a
 * {@link net.minecraft.core.BlockPos}
 */
@Getter
@Setter
@NoArgsConstructor
public class SpaceTimeCoordinate implements INBTSerializable<CompoundTag> {
	public static Codec<SpaceTimeCoordinate> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Codec.DOUBLE.fieldOf("x").forGetter(SpaceTimeCoordinate::GetX),
					Codec.DOUBLE.fieldOf("y").forGetter(SpaceTimeCoordinate::GetY),
					Codec.DOUBLE.fieldOf("z").forGetter(SpaceTimeCoordinate::GetZ),
					Codec.DOUBLE.fieldOf("time").forGetter(SpaceTimeCoordinate::GetTime),
					Level.RESOURCE_KEY_CODEC.fieldOf("level").forGetter(SpaceTimeCoordinate::getLevelKey))
			.apply(instance, SpaceTimeCoordinate::new));

	double Time = 0, X = 0, Y = 0, Z = 0;
	ResourceKey<Level> level = Level.OVERWORLD;

	public SpaceTimeCoordinate(BlockPos pos) {
		this.X = pos.getX();
		this.Y = pos.getY();
		this.Z = pos.getZ();
	}

	public SpaceTimeCoordinate(Vec3 pos) {
		this.X = pos.x();
		this.Y = pos.y();
		this.Z = pos.z();
	}

	public SpaceTimeCoordinate(BlockPos pos, ResourceKey<Level> level) {
		this.X = pos.getX();
		this.Y = pos.getY();
		this.Z = pos.getZ();
		this.level = level;
	}

	public SpaceTimeCoordinate(double x, double y, double z) {
		X = x;
		Y = y;
		Z = z;
	}

	public SpaceTimeCoordinate(double x, double y, double z, double time) {
		X = x;
		Y = y;
		Z = z;
		Time = time;
	}

	public SpaceTimeCoordinate(double x, double y, double z, double time, ResourceKey<Level> level) {
		X = x;
		Y = y;
		Z = z;
		Time = time;
		this.level = level;
	}

	public SpaceTimeCoordinate AddX(double x) {
		this.X += x;
		return this;
	}

	public SpaceTimeCoordinate AddY(double y) {
		this.Y += y;
		return this;
	}

	public SpaceTimeCoordinate AddZ(double z) {
		this.Z += z;
		return this;
	}

	public BlockPos GetBlockPos() {
		return new BlockPos((int) this.X, (int) this.Y, (int) this.Z);
	}

	public double GetTime() {
		return this.Time;
	}

	public double GetX() {
		return this.X;
	}

	public double GetY() {
		return this.Y;
	}

	public double GetZ() {
		return this.Z;
	}

	public String ReadableString() {
		return "X | " + Double.toString(this.X) + " Y | " + Double.toString(this.Y) + " Z | " + Double.toString(this.Z);
	}

	public String ReadableStringShort() {
		return Integer.toString((int) this.X) + " | " + Integer.toString((int) this.Y) + " | "
				+ Integer.toString((int) this.Z) + " | " + this.level.location();
	}

	public SpaceTimeCoordinate copy() {
		return new SpaceTimeCoordinate(X, Y, Z, Time, level);
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.Time = nbt.getDouble("time");
		this.X = nbt.getDouble("x");
		this.Y = nbt.getDouble("y");
		this.Z = nbt.getDouble("z");
		this.level = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(nbt.getString("levelLoc")));
	}

	public ServerLevel getLevel() {
		return ServerLifecycleHooks.getCurrentServer().getLevel(this.level);
	}

	public ResourceKey<Level> getLevelKey() {
		return this.level;
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putDouble("time", this.Time);
		tag.putDouble("x", this.X);
		tag.putDouble("y", this.Y);
		tag.putDouble("z", this.Z);
		tag.putString("levelLoc", this.level.location().getNamespace() + ":" + this.level.location().getPath());
		return tag;
	}

	@Override
	public String toString() {
		return this.ReadableString();
	}
}
