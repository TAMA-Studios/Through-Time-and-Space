/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.memory_management;

import org.lwjgl.system.MemoryUtil;

public class IndexedArena implements ImAnArena {
	long address, offset;

	public IndexedArena(long blockSize) {
		this.address = MemoryUtil.nmemAlloc(1024 * 1024); // Allocate 1MiB to the arena
		this.offset = 0;
		MemoryUtil.memPutLong(this.address, blockSize); // Set a long at position 0 to be the blocksize
	}

	public long alloc() throws MemAccessException {
		if (Long.BYTES + ((this.offset + 1) * getBlockSize()) > 1024 * 1024)
			throw new MemAccessException("Error: Exceeded arena size!");
		return this.offset++;
	}

	@Override
	public long alloc(long toAlloc) throws MemAccessException {
		if (toAlloc > getBlockSize())
			throw new MemAccessException("Error: Amount to allocate exceeds arena's block size!");
		return this.alloc();
	}

	public long getAddr(long index) {
		return (Long.BYTES + this.address) + (index * getBlockSize());
	}

	public long getBlockSize() {
		return MemoryUtil.memGetLong(this.address);
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
