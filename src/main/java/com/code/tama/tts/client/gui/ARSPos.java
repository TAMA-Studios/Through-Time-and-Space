package com.code.tama.tts.client.gui;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.Getter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

/**
 * Coordinate of a single ARS grid cell. One cell is 3x3x3 sections
 * (48x48x48 blocks) - the same relationship ChunkPos has to blocks (16),
 * just one size class up and extended to the Y axis as well.
 */
@Getter
public final class ARSPos {
    public static Codec<ARSPos> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(Codec.INT.fieldOf("x").forGetter(ARSPos::getX),
                    Codec.INT.fieldOf("y").forGetter(ARSPos::getY),
                    Codec.INT.fieldOf("z").forGetter(ARSPos::getZ))
            .apply(instance, ARSPos::new));


    /** Size in blocks of one ARS grid cell along each axis. */
    public static final int CELL_SIZE = 48;

    private final int x;
    private final int y;
    private final int z;

    public ARSPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /** Which ARS cell a given block position falls inside. */
    public static ARSPos fromBlockPos(BlockPos pos) {
        return new ARSPos(
                Math.floorDiv(pos.getX(), CELL_SIZE),
                Math.floorDiv(pos.getY(), CELL_SIZE),
                Math.floorDiv(pos.getZ(), CELL_SIZE)
        );
    }

    /** Lowest-corner block position of this cell. */
    public BlockPos getOrigin() {
        return new BlockPos(x * CELL_SIZE, y * CELL_SIZE, z * CELL_SIZE);
    }

    /** Block position at the center of this cell. */
    public BlockPos getCenter() {
        return getOrigin().offset(CELL_SIZE / 2, CELL_SIZE / 2, CELL_SIZE / 2);
    }

    public CompoundTag serialize() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("X", x);
        tag.putInt("Y", y);
        tag.putInt("Z", z);
        return tag;
    }

    public static ARSPos deserialize(CompoundTag tag) {
        return new ARSPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ARSPos arsPos)) return false;
        return x == arsPos.x && y == arsPos.y && z == arsPos.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "ARSPos[" + x + ", " + y + ", " + z + "]";
    }
}