/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.memory_management;

import lombok.Getter;
import org.lwjgl.system.MemoryUtil;

public class IndexedFlexibleArena implements ImAnArena {

	@Getter
	private long address;
	private long offset;
	private final long maxSize;

	private final long[] index;
	private long indexCount;

	/**
	 * This constructor allocates 1MB of RAM, with 100_000 max allocations.
	 *
	 * @throws MemAccessException
	 */
	public IndexedFlexibleArena() throws MemAccessException {
		this(1024 * 1024, 100_000);
	}

	/**
	 * This constructor allocates with 100_000 max allocations.
	 *
	 * @throws MemAccessException
	 */
	public IndexedFlexibleArena(long maxSize) throws MemAccessException {
		this(maxSize, 100_000);
	}

	public IndexedFlexibleArena(long maxSize, int maxAllocations) throws MemAccessException {
		if (maxSize <= 0)
			throw new MemAccessException("Arena size must be greater than zero!");

		if (maxAllocations <= 0)
			throw new MemAccessException("Maximum allocation count must be greater than zero!");

		this.address = MemoryUtil.nmemAlloc(maxSize);
		this.offset = 0;
		this.maxSize = maxSize;
		this.index = new long[maxAllocations];
		this.indexCount = 0;
	}

	@Override
	public long alloc() throws MemAccessException {
		throw new MemAccessException("This method is not implemented in an IndexedFlexibleArena!");
	}

	@Override
	public long alloc(long size) throws MemAccessException {
		if (size <= 0)
			throw new MemAccessException("Allocation size must be greater than zero!");

		if (offset + size > maxSize)
			throw new MemAccessException("Error: Amount to allocate exceeds arena's maximum allocated size!");

		if (indexCount >= index.length)
			throw new MemAccessException("Error: Maximum number of allocations exceeded!");

		long allocationIndex = indexCount++;

		index[Math.toIntExact(allocationIndex)] = offset;
		offset += size;

		return allocationIndex;
	}

	public long getAddr(long index) {
		return address + this.index[Math.toIntExact(index)];
	}

	@Override
	public void close() {
		if (address != 0) {
			MemoryUtil.nmemFree(address);
			address = 0;
		}

		offset = 0;
		indexCount = 0;
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