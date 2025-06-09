/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxelRotatedShape {

    public final VoxelShape N;
    public final VoxelShape E;
    public final VoxelShape S;
    public final VoxelShape W;

    public VoxelRotatedShape(VoxelShape shape){
        this.N = shape;
        this.E = RotateVoxelShape(shape, Direction.EAST);
        this.S = RotateVoxelShape(shape, Direction.SOUTH);
        this.W = RotateVoxelShape(shape, Direction.WEST);
    }

    public VoxelShape GetShapeFromRotation(Direction dir){
        switch(dir){
            default: return this.N;
            case EAST: return this.E;
            case SOUTH: return this.S;
            case WEST: return this.W;
        }
    }

    public static VoxelShape RotateVoxelShape(VoxelShape shape, Direction dir){
        VoxelShape newShape = Shapes.empty();

        for(AABB bb : shape.toAabbs()){
            newShape = Shapes.join(newShape, Shapes.create(RotateAABB(bb.move(-0.5, 0, -0.5), dir).move(0.5, 0, 0.5)), BooleanOp.OR);
        }

        return newShape;
    }

    public static AABB RotateAABB(AABB aabb, Direction dir){
        return new AABB(RotateVec3(new Vec3(aabb.minX, aabb.minY, aabb.minZ), dir), RotateVec3(new Vec3(aabb.maxX, aabb.maxY, aabb.maxZ), dir));
    }

    public static Vec3 RotateVec3(Vec3 point, Direction dir){
        return switch (dir) {
            case EAST -> new Vec3(-point.z, point.y, point.x);
            case SOUTH -> new Vec3(-point.x, point.y, -point.z);
            case WEST -> new Vec3(point.z, point.y, -point.x);
            default -> point;
        };
    }

}
