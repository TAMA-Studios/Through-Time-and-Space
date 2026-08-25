/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.memory_management.types;

import org.lwjgl.system.MemoryUtil;

import net.minecraft.world.phys.Vec3;

import com.code.tama.triggerapi.memory_management.ImAnArena;
import com.code.tama.triggerapi.memory_management.MemAccessException;

public class NativeVec3 {
	public static final long SIZE = Double.BYTES * 3;
	public static final long X_OFFSET = 0;
	public static final long Y_OFFSET = Double.BYTES;
	public static final long Z_OFFSET = Double.BYTES * 2;

	public static long create() {
		return MemoryUtil.nmemAlloc(SIZE);
	}

	public static long create(Vec3 vec) {
		long addr = MemoryUtil.nmemAlloc(SIZE);
		set(addr, vec);

		return addr;
	}

	/**
	 * @return An address to a Vec3Mem which is a copy of the provided address
	 *         Vec3Mem data
	 */
	public static long create(long address) {
		long destAddr = MemoryUtil.nmemAlloc(SIZE);
		MemoryUtil.memCopy(address, destAddr, SIZE);
		return destAddr;
	}

	public static long create(ImAnArena arena) throws MemAccessException {
		return arena.alloc(SIZE);
	}

	public static void set(long addr, Vec3 vec) {
		setX(addr, vec.x);
		setY(addr, vec.y);
		setZ(addr, vec.z);
	}

	public static double getX(long address) {
		return MemoryUtil.memGetDouble(address + X_OFFSET);
	}

	public static double getY(long address) {
		return MemoryUtil.memGetDouble(address + Y_OFFSET);
	}

	public static double getZ(long address) {
		return MemoryUtil.memGetDouble(address + Z_OFFSET);
	}

	public static void setX(long address, double value) {
		MemoryUtil.memPutDouble(address + X_OFFSET, value);
	}

	public static void setY(long address, double value) {
		MemoryUtil.memPutDouble(address + Y_OFFSET, value);
	}

	public static void setZ(long address, double value) {
		MemoryUtil.memPutDouble(address + Z_OFFSET, value);
	}

	public static void addNoAlloc(long address, long secondAddr) {
		setX(address, getX(address) + getX(secondAddr));
		setY(address, getY(address) + getY(secondAddr));
		setZ(address, getZ(address) + getZ(secondAddr));
	}

	public static void addNoAlloc(long address, double x, double y, double z) {
		setX(address, getX(address) + x);
		setY(address, getY(address) + y);
		setZ(address, getZ(address) + z);
	}

	public static long addAlloc(long address, long secondAddr) {
		long toRet = MemoryUtil.nmemAlloc(SIZE);
		setX(toRet, getX(address) + getX(secondAddr));
		setY(toRet, getY(address) + getY(secondAddr));
		setZ(toRet, getZ(address) + getZ(secondAddr));
		return toRet;
	}

	public static long addAlloc(long address, double x, double y, double z) {
		long toRet = MemoryUtil.nmemAlloc(SIZE);
		setX(toRet, getX(address) + x);
		setY(toRet, getY(address) + y);
		setZ(toRet, getZ(address) + z);
		return toRet;
	}

	public static long addAlloc(ImAnArena arena, long address, long secondAddr) throws MemAccessException {
		long offset = arena.alloc(SIZE);
		long addr = arena.getAddr(offset);

		setX(addr, getX(address) + getX(secondAddr));
		setY(addr, getY(address) + getY(secondAddr));
		setZ(addr, getZ(address) + getZ(secondAddr));
		return offset;
	}

	public static long addAlloc(ImAnArena arena, long address, double x, double y, double z) throws MemAccessException {
		long offset = arena.alloc(SIZE);
		long addr = arena.getAddr(offset);

		setX(addr, getX(address) + x);
		setY(addr, getY(address) + y);
		setZ(addr, getZ(address) + z);
		return offset;
	}

	public static void multiply(long address, long destAddr, double x, double y, double z) {
		MemoryUtil.memCopy(address, destAddr, SIZE);
		multiply(destAddr, x, y, z);
	}

	public static void multiply(long address, double x, double y, double z) {
		setX(address, getX(address) * x);
		setY(address, getY(address) * y);
		setZ(address, getZ(address) * z);
	}

	public static void scale(long address, double p_82491_) {
		multiply(address, p_82491_, p_82491_, p_82491_);
	}

	public static double distanceToSqr(long address, long secondAddress) {
		double d0 = getX(address) - getX(secondAddress);
		double d1 = getY(address) - getY(secondAddress);
		double d2 = getZ(address) - getZ(secondAddress);
		return d0 * d0 + d1 * d1 + d2 * d2;
	}

	public static double distanceToSqr(long address, double p_82532_, double p_82533_, double p_82534_) {
		double d0 = p_82532_ - getX(address);
		double d1 = p_82533_ - getY(address);
		double d2 = p_82534_ - getZ(address);
		return d0 * d0 + d1 * d1 + d2 * d2;
	}

	public static void free(long address) {
		MemoryUtil.nmemFree(address);
	}
}
