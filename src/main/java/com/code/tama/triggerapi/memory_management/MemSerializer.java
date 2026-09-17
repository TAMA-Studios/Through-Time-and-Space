/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.memory_management;

import java.lang.reflect.Field;

public class MemSerializer {
	public static long serialize(Class<?> clazz) throws MemAccessException, IllegalAccessException {
		Field fields[] = clazz.getDeclaredFields();

		long sizeInBytes = 0;

		// First pass to get byte size
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (!f.getType().isPrimitive())
				continue;

			if (f.getType().getName().equals(byte.class.getName()))
				sizeInBytes += Byte.BYTES;
			if (f.getType().getName().equals(short.class.getName()))
				sizeInBytes += Short.BYTES;
			if (f.getType().getName().equals(int.class.getName()))
				sizeInBytes += Integer.BYTES;
			if (f.getType().getName().equals(float.class.getName()))
				sizeInBytes += Float.BYTES;
			if (f.getType().getName().equals(double.class.getName()))
				sizeInBytes += Double.BYTES;
			if (f.getType().getName().equals(long.class.getName()))
				sizeInBytes += Long.BYTES;
		}

		IndexedFlexibleArena object = new IndexedFlexibleArena(sizeInBytes);

		// Second pass to serialize fields
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			if (!f.getType().isPrimitive())
				continue;

			long offset = object.alloc();

			if (f.getType().getName().equals(byte.class.getName()))
				object.setByte(offset, f.getByte(clazz));
			if (f.getType().getName().equals(short.class.getName()))
				object.setShort(offset, f.getShort(clazz));
			if (f.getType().getName().equals(int.class.getName()))
				object.setInt(offset, f.getInt(clazz));
			if (f.getType().getName().equals(float.class.getName()))
				object.setFloat(offset, f.getFloat(clazz));
			if (f.getType().getName().equals(double.class.getName()))
				object.setDouble(offset, f.getDouble(clazz));
			if (f.getType().getName().equals(long.class.getName()))
				object.setLong(offset, f.getLong(clazz));
		}

		return object.getAddress();
	}
}
