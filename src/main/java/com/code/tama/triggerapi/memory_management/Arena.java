/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.memory_management;

import org.lwjgl.system.MemoryUtil;

public class Arena implements ImAnArena {
	long address, offset;

	public Arena() {
		this.address = MemoryUtil.nmemAlloc(1024 * 1024); // Allocate 1MiB to the arena
		this.offset = 0;
	}

	public long alloc(long toAlloc) throws MemAccessException {
		long toRet = offset;
		if (offset + toAlloc > 1024 * 1024)
			throw new MemAccessException("Error: Exceeded arena size!");
		this.offset += toAlloc;

		return toRet;
	}

	/**
	 * @return The address of a long-sized block in this Arena
	 */
	@Override
	public long alloc() throws MemAccessException {
		return alloc(Long.BYTES);
	}

	public long getAddr(long arenaAddress) {
		return this.address + arenaAddress;
	}

	@Override
	public void close() {
		if (address != 0) {
			MemoryUtil.nmemFree(address);
			address = 0;
		}

		offset = 0;
	}

	@Override
	public byte getByte(long offset) {
		return MemoryUtil.memGetByte(getAddr(offset));
	}

	@Override
	public short getShort(long offset) {
		return MemoryUtil.memGetShort(getAddr(offset));
	}

	@Override
	public int getInt(long offset) {
		return MemoryUtil.memGetInt(getAddr(offset));
	}

	@Override
	public float getFloat(long offset) {
		return MemoryUtil.memGetFloat(getAddr(offset));
	}

	@Override
	public double getDouble(long offset) {
		return MemoryUtil.memGetDouble(getAddr(offset));
	}

	@Override
	public long getLong(long offset) {
		return MemoryUtil.memGetLong(getAddr(offset));
	}

	@Override
	public void setByte(long offset, byte value) {
		MemoryUtil.memPutByte(getAddr(offset), value);
	}

	@Override
	public void setShort(long offset, short value) {
		MemoryUtil.memPutShort(getAddr(offset), value);
	}

	@Override
	public void setInt(long offset, int value) {
		MemoryUtil.memPutInt(getAddr(offset), value);
	}

	@Override
	public void setFloat(long offset, float value) {
		MemoryUtil.memPutFloat(getAddr(offset), value);
	}

	@Override
	public void setDouble(long offset, double value) {
		MemoryUtil.memPutDouble(getAddr(offset), value);
	}

	@Override
	public void setLong(long offset, long value) {
		MemoryUtil.memPutLong(getAddr(offset), value);
	}
}
