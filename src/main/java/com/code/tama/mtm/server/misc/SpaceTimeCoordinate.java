package com.code.tama.mtm.server.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

/** Holds four doubles, X, Y, Z, and Time
 * Can be NBT serialized/deserialized (unlike {@link net.minecraft.core.BlockPos})
 * Can be created using a {@link net.minecraft.core.BlockPos}**/
public class SpaceTimeCoordinate implements INBTSerializable<CompoundTag> {
    double Time, X, Y, Z;

    public SpaceTimeCoordinate(double X, double Y, double Z, double Time) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;
        this.Time = Time;
    }

    public SpaceTimeCoordinate(BlockPos pos) {
        this.X = pos.getX();
        this.Y = pos.getY();
        this.Z = pos.getZ();
    }

    public SpaceTimeCoordinate() {}

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

    public static SpaceTimeCoordinate of(CompoundTag tag){
        SpaceTimeCoordinate SpaceTimeCoordinates = new SpaceTimeCoordinate();
        SpaceTimeCoordinates.deserializeNBT(tag);
        return SpaceTimeCoordinates;
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

    public double GetTime() {
        return this.Time;
    }

    public String ReadableString() {
        return "X - " + Double.toString(this.X) + " Y - " + Double.toString(this.Y) + " Z - " + Double.toString(this.Z);
    }

    public String ReadableStringShort() {
        return Integer.toString((int) this.X) + ", " + Integer.toString((int) this.Y) + ", " + Integer.toString((int) this.Z);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("time", this.Time);
        tag.putDouble("x", this.X);
        tag.putDouble("y", this.Y);
        tag.putDouble("z", this.Z);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.Time = nbt.getDouble("time");
        this.X = nbt.getDouble("x");
        this.Y = nbt.getDouble("y");
        this.Z = nbt.getDouble("z");
    }
}