/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.misc.containers;

import com.code.tama.memory_management.Struct;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.NoArgsConstructor;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import com.code.tama.triggerapi.memory_management.ImAnArena;
import com.code.tama.triggerapi.memory_management.MemAccessException;

/**
 * Holds three doubles, X, Y, Z. Can be serialized/deserialized via Codec.
 * <br />
 * Extends INBTSerializable<CompoundTag> <br />
 * Designed as a "lite" version of the SpaceTimeCoordinate
 */
@NoArgsConstructor
@Struct
public class SpaceCoordinate implements INBTSerializable<CompoundTag> {
	public static Codec<SpaceCoordinate> CODEC = RecordCodecBuilder
			.create(instance -> instance
					.group(Codec.DOUBLE.fieldOf("x").forGetter(SpaceCoordinate::GetX),
							Codec.DOUBLE.fieldOf("y").forGetter(SpaceCoordinate::GetY),
							Codec.DOUBLE.fieldOf("z").forGetter(SpaceCoordinate::GetZ))
					.apply(instance, SpaceCoordinate::new));

	double X = 0, Y = 0, Z = 0;

	public static long memCreate(BlockPos pos) {
		return memCreate(pos.getX(), pos.getY(), pos.getZ());
	}

	public static long memCreate(ImAnArena arena, BlockPos pos) throws MemAccessException {
		return memCreate(arena, pos.getX(), pos.getY(), pos.getZ());
	}

	public static long memCreate(ImAnArena arena, Position pos) throws MemAccessException {
		return memCreate(arena, pos.x(), pos.y(), pos.z());
	}

	public static long memCreate(Position pos) {
		return memCreate(pos.x(), pos.y(), pos.z());
	}

	public static long memCreate(double x, double y, double z) {
		return memSet(NativeSpaceCoordinate.create(), x, y, z);
	}

	public static long memSet(long addr, double x, double y, double z) {
		NativeSpaceCoordinate.setX(addr, x);
		NativeSpaceCoordinate.setY(addr, y);
		NativeSpaceCoordinate.setZ(addr, z);
		return addr;
	}

	public static long memSet(long addr, Position pos) {
		memSet(addr, pos.x(), pos.y(), pos.z());
		return addr;
	}

	public static long memSet(long addr, BlockPos pos) {
		memSet(addr, pos.getX(), pos.getY(), pos.getZ());
		return addr;
	}

	public static long memCreate(ImAnArena arena, double x, double y, double z) throws MemAccessException {
		return memSet(NativeSpaceCoordinate.create(arena), x, y, z);
	}

	public SpaceCoordinate(BlockPos pos) {
		this.X = pos.getX();
		this.Y = pos.getY();
		this.Z = pos.getZ();
	}

	public SpaceCoordinate(Position pos) {
		this.X = pos.x();
		this.Y = pos.y();
		this.Z = pos.z();
	}

	public SpaceCoordinate(double x, double y, double z) {
		X = x;
		Y = y;
		Z = z;
	}

	public SpaceCoordinate AddX(double x) {
		this.X += x;
		return this;
	}

	public SpaceCoordinate AddY(double y) {
		this.Y += y;
		return this;
	}

	public SpaceCoordinate AddZ(double z) {
		this.Z += z;
		return this;
	}

	public BlockPos GetBlockPos() {
		return new BlockPos((int) this.X, (int) this.Y, (int) this.Z);
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
		return "X | " + this.X + " Y | " + this.Y + " Z | " + this.Z;
	}

	public String ReadableStringShort() {
		return (int) this.X + " | " + (int) this.Y + " | " + (int) this.Z;
	}

	public SpaceCoordinate copy() {
		return new SpaceCoordinate(X, Y, Z);
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		this.X = nbt.getDouble("x");
		this.Y = nbt.getDouble("y");
		this.Z = nbt.getDouble("z");
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putDouble("x", this.X);
		tag.putDouble("y", this.Y);
		tag.putDouble("z", this.Z);
		return tag;
	}

	@Override
	public String toString() {
		return this.ReadableString();
	}
}
