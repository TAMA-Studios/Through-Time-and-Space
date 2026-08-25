/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.memory_management;

public interface ImAnArena extends AutoCloseable {
	long alloc() throws MemAccessException;
	long alloc(long toAlloc) throws MemAccessException;
	long getAddr(long offset);

	byte getByte(long offset);
	short getShort(long offset);
	int getInt(long offset);
	float getFloat(long offset);
	double getDouble(long offset);
	long getLong(long offset);

	void setByte(long offset, byte value);
	void setShort(long offset, short value);
	void setInt(long offset, int value);
	void setFloat(long offset, float value);
	void setDouble(long offset, double value);
	void setLong(long offset, long value);
}
