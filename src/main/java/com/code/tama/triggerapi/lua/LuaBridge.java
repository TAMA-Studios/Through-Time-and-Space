/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import static com.code.tama.triggerapi.lua.LuaExecutable.DEBUG;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.*;

public class LuaBridge {

	// Cache for reflection data
	private static final Map<Class<?>, List<FieldInfo>> FIELD_CACHE = new ConcurrentHashMap<>();

	private record FieldInfo(Field field, String luaName) {
	}

	/**
	 * Converts a Lua value to a Java object of the specified type
	 */
	private static Object luaToJava(LuaValue value, Class<?> targetType) {
		return luaToJava(value, targetType, new HashSet<>());
	}

	private static Object luaToJava(LuaValue value, Class<?> targetType, Set<LuaTable> visited) {
		// Handle nil
		if (value.isnil()) {
			return null;
		}

		// Primitives and boxed types
		if (targetType == int.class || targetType == Integer.class) {
			return value.checkint();
		}
		if (targetType == long.class || targetType == Long.class) {
			return value.checklong();
		}
		if (targetType == double.class || targetType == Double.class) {
			return value.checkdouble();
		}
		if (targetType == float.class || targetType == Float.class) {
			return (float) value.checkdouble();
		}
		if (targetType == boolean.class || targetType == Boolean.class) {
			return value.checkboolean();
		}
		if (targetType == String.class) {
			return value.checkjstring();
		}
		if (targetType == byte.class || targetType == Byte.class) {
			return (byte) value.checkint();
		}
		if (targetType == short.class || targetType == Short.class) {
			return (short) value.checkint();
		}
		if (targetType == char.class || targetType == Character.class) {
			String s = value.checkjstring();
			if (s.length() != 1) {
				throw new LuaError("Expected single character, got: " + s);
			}
			return s.charAt(0);
		}

		// Raw LuaValue passthrough
		if (LuaValue.class.isAssignableFrom(targetType)) {
			return value;
		}

		// Arrays
		if (targetType.isArray()) {
			return luaTableToArray(value.checktable(), targetType.getComponentType(), visited);
		}

		// Lists
		if (List.class.isAssignableFrom(targetType)) {
			return luaTableToList(value.checktable(), visited);
		}

		// Maps
		if (Map.class.isAssignableFrom(targetType)) {
			return luaTableToMap(value.checktable(), visited);
		}

		// LuaSerializable objects
		if (LuaSerializable.class.isAssignableFrom(targetType)) {
			LuaTable table = value.checktable();

			// Check for circular reference
			if (visited.contains(table)) {
				throw new LuaError("Circular reference detected during deserialization");
			}
			visited.add(table);

			// Determine actual type to instantiate
			Class<?> actualType = targetType;
			if (targetType == LuaSerializable.class || targetType.isInterface()
					|| Modifier.isAbstract(targetType.getModifiers())) {

				LuaValue classValue = table.get("__javaClass");
				if (classValue.isstring()) {
					try {
						actualType = Class.forName(classValue.checkjstring());
					} catch (ClassNotFoundException e) {
						throw new LuaError("Unknown class: " + classValue.checkjstring());
					}
				} else {
					throw new LuaError("Cannot instantiate " + targetType.getName() + " - no __javaClass tag found");
				}
			}

			try {
				// Create instance
				Constructor<?> constructor = actualType.getDeclaredConstructor();
				constructor.setAccessible(true);
				Object instance = constructor.newInstance();

				// Populate fields
				for (FieldInfo fieldInfo : getCachedFields(actualType)) {
					LuaValue luaVal = table.get(fieldInfo.luaName);
					if (!luaVal.isnil()) {
						Object javaVal = luaToJava(luaVal, fieldInfo.field.getType(), visited);
						fieldInfo.field.set(instance, javaVal);
					}
				}

				visited.remove(table);
				return instance;

			} catch (NoSuchMethodException e) {
				throw new LuaError(actualType.getName() + " must have a no-argument constructor");
			} catch (Exception e) {
				throw new LuaError("Failed to deserialize " + actualType.getName() + ": " + e.getClass().getSimpleName()
						+ " - " + e.getMessage());
			}
		}

		throw new IllegalArgumentException("Unsupported parameter type: " + targetType.getName());
	}

	/**
	 * Converts a Java object to a Lua value
	 */
	private static LuaValue javaToLua(Object obj) {
		return javaToLua(obj, new IdentityHashMap<>());
	}

	private static LuaValue javaToLua(Object obj, IdentityHashMap<Object, LuaValue> visited) {
		if (obj == null)
			return LuaValue.NIL;

		// Check for circular reference
		if (visited.containsKey(obj)) {
			throw new LuaError("Circular reference detected during serialization");
		}

		// Primitives and strings
		if (obj instanceof Integer i)
			return LuaValue.valueOf(i);
		if (obj instanceof Long l)
			return LuaValue.valueOf(l.doubleValue());
		if (obj instanceof Double d)
			return LuaValue.valueOf(d);
		if (obj instanceof Float f)
			return LuaValue.valueOf(f);
		if (obj instanceof Boolean b)
			return LuaValue.valueOf(b);
		if (obj instanceof String s)
			return LuaValue.valueOf(s);
		if (obj instanceof Byte b)
			return LuaValue.valueOf(b);
		if (obj instanceof Short s)
			return LuaValue.valueOf(s);
		if (obj instanceof Character c)
			return LuaValue.valueOf(c.toString());

		// Mark as visited for circular reference detection
		visited.put(obj, LuaValue.NIL); // Placeholder

		try {
			// Arrays
			if (obj.getClass().isArray()) {
				LuaTable table = arrayToLuaTable(obj, visited);
				visited.put(obj, table);
				return table;
			}

			// Lists
			if (obj instanceof List<?> list) {
				LuaTable table = listToLuaTable(list, visited);
				visited.put(obj, table);
				return table;
			}

			// Maps
			if (obj instanceof Map<?, ?> map) {
				LuaTable table = mapToLuaTable(map, visited);
				visited.put(obj, table);
				return table;
			}

			// LuaSerializable objects
			if (obj instanceof LuaSerializable) {
				LuaTable table = new LuaTable();
				visited.put(obj, table); // Register before recursion

				table.set("__javaClass", obj.getClass().getName());

				for (FieldInfo fieldInfo : getCachedFields(obj.getClass())) {
					try {
						Object value = fieldInfo.field.get(obj);
						table.set(fieldInfo.luaName, javaToLua(value, visited));
					} catch (IllegalAccessException e) {
						throw new LuaError("Cannot access field " + fieldInfo.field.getName());
					}
				}

				return table;
			}

			visited.remove(obj); // Not serializable, don't keep in visited
			throw new IllegalArgumentException("Cannot convert " + obj.getClass().getName() + " to Lua");

		} catch (Exception e) {
			visited.remove(obj);
			throw e;
		}
	}

	/**
	 * Convert Java array to Lua table (1-indexed)
	 */
	private static LuaTable arrayToLuaTable(Object array, IdentityHashMap<Object, LuaValue> visited) {
		LuaTable table = new LuaTable();
		int length = Array.getLength(array);

		for (int i = 0; i < length; i++) {
			Object element = Array.get(array, i);
			table.set(i + 1, javaToLua(element, visited)); // Lua is 1-indexed
		}

		return table;
	}

	/**
	 * Convert Java List to Lua table (1-indexed)
	 */
	private static LuaTable listToLuaTable(List<?> list, IdentityHashMap<Object, LuaValue> visited) {
		LuaTable table = new LuaTable();

		for (int i = 0; i < list.size(); i++) {
			table.set(i + 1, javaToLua(list.get(i), visited)); // Lua is 1-indexed
		}

		return table;
	}

	/**
	 * Convert Java Map to Lua table
	 */
	private static LuaTable mapToLuaTable(Map<?, ?> map, IdentityHashMap<Object, LuaValue> visited) {
		LuaTable table = new LuaTable();

		for (Map.Entry<?, ?> entry : map.entrySet()) {
			LuaValue key = javaToLua(entry.getKey(), visited);
			LuaValue value = javaToLua(entry.getValue(), visited);
			table.set(key, value);
		}

		return table;
	}

	/**
	 * Convert Lua table to Java array
	 */
	private static Object luaTableToArray(LuaTable table, Class<?> componentType, Set<LuaTable> visited) {
		if (visited.contains(table)) {
			throw new LuaError("Circular reference detected in array");
		}
		visited.add(table);

		// Count array length (Lua arrays are 1-indexed)
		int length = 0;
		while (!table.get(length + 1).isnil()) {
			length++;
		}

		Object array = Array.newInstance(componentType, length);

		for (int i = 0; i < length; i++) {
			LuaValue element = table.get(i + 1); // Lua is 1-indexed
			Object javaElement = luaToJava(element, componentType, visited);
			Array.set(array, i, javaElement);
		}

		visited.remove(table);
		return array;
	}

	/**
	 * Convert Lua table to Java List
	 */
	private static List<Object> luaTableToList(LuaTable table, Set<LuaTable> visited) {
		if (visited.contains(table)) {
			throw new LuaError("Circular reference detected in list");
		}
		visited.add(table);

		List<Object> list = new ArrayList<>();

		// Iterate through array portion (1-indexed)
		int index = 1;
		while (true) {
			LuaValue element = table.get(index);
			if (element.isnil())
				break;

			// For lists, we can't know the generic type, so we try to infer
			list.add(luaToJavaAuto(element, visited));
			index++;
		}

		visited.remove(table);
		return list;
	}

	/**
	 * Convert Lua table to Java Map
	 */
	private static Map<Object, Object> luaTableToMap(LuaTable table, Set<LuaTable> visited) {
		if (visited.contains(table)) {
			throw new LuaError("Circular reference detected in map");
		}
		visited.add(table);

		Map<Object, Object> map = new HashMap<>();

		// Iterate through all key-value pairs
		LuaValue key = LuaValue.NIL;
		while (true) {
			Varargs next = table.next(key);
			key = next.arg1();
			if (key.isnil())
				break;

			LuaValue value = next.arg(2);
			map.put(luaToJavaAuto(key, visited), luaToJavaAuto(value, visited));
		}

		visited.remove(table);
		return map;
	}

	/**
	 * Auto-detect type and convert Lua to Java (for collections without type info)
	 */
	private static Object luaToJavaAuto(LuaValue value, Set<LuaTable> visited) {
		if (value.isnil())
			return null;
		if (value.isboolean())
			return value.checkboolean();
		if (value.isint())
			return value.checkint();
		if (value.isnumber())
			return value.checkdouble();
		if (value.isstring())
			return value.checkjstring();

		if (value.istable()) {
			LuaTable table = value.checktable();

			// Check if it's a LuaSerializable object
			LuaValue classTag = table.get("__javaClass");
			if (classTag.isstring()) {
				try {
					Class<?> clazz = Class.forName(classTag.checkjstring());
					return luaToJava(value, clazz, visited);
				} catch (ClassNotFoundException e) {
					// Fall through to treat as regular table
				}
			}

			// Check if it looks like an array (has numeric keys starting at 1)
			if (!table.get(1).isnil()) {
				return luaTableToList(table, visited);
			}

			// Otherwise treat as map
			return luaTableToMap(table, visited);
		}

		return value; // Return raw LuaValue if we can't convert
	}

	// ========== UNSAFE OPERATIONS (for third-party classes) ==========

	/**
	 * Converts ANY Java object to Lua, using reflection on all accessible fields.
	 * WARNING: This bypasses @LuaField annotations and may expose internal state.
	 * Use only for classes you don't control (Minecraft, libraries, etc.)
	 */
	public static LuaValue unsafeJavaToLua(Object obj) {
		return unsafeJavaToLua(obj, new IdentityHashMap<>());
	}

	private static LuaValue unsafeJavaToLua(Object obj, IdentityHashMap<Object, LuaValue> visited) {
		if (obj == null)
			return LuaValue.NIL;

		// Try normal conversion first
		try {
			return javaToLua(obj, visited);
		} catch (IllegalArgumentException e) {
			// Not a standard type, use reflection
		}

		// Check for circular reference
		if (visited.containsKey(obj)) {
			throw new LuaError("Circular reference detected during unsafe serialization");
		}

		Class<?> clazz = obj.getClass();

		// Skip Java/JDK internal classes that might cause issues
		if (clazz.getName().startsWith("java.lang.reflect") || clazz.getName().startsWith("sun.")
				|| clazz.getName().startsWith("jdk.")) {
			return LuaValue.valueOf(obj.toString());
		}

		LuaTable table = new LuaTable();
		visited.put(obj, table);

		try {
			// Add class tag
			table.set("__javaClass", clazz.getName());
			table.set("__unsafe", LuaValue.valueOf(true)); // Mark as unsafe serialization

			// Serialize all accessible methods
			for (Method method : obj.getClass().getMethods()) {
				// Skip weird / synthetic methods
				if (method.isSynthetic() || method.isBridge())
					continue;

				try {
					method.setAccessible(true);
					int paramCount = method.getParameterCount();
					LibFunction function = createFunctionWrapper(obj, method, paramCount);

					if (function != null) {
						table.set(method.getName(), function);
						DEBUG("[FIELD] GOOD: " + method.getName() + " CLASS: " + clazz.getName());
					}
				} catch (Exception e) {
					// Skip problematic fields but log them
					table.set(method.getName() + "_error", e.getClass().getSimpleName());
					DEBUG("[FIELD] BAD: " + method.getName() + " CLASS: " + clazz.getName());
				}
			}

			// Serialize all accessible fields
			for (Field field : getAllFields(clazz)) {
				// Skip static and synthetic fields
				if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
					continue;
				}

				try {
					field.setAccessible(true);
					Object value = field.get(obj);

					// Skip null values to reduce clutter
					if (value != null) {
						table.set(field.getName(), unsafeJavaToLua(value, visited));
						DEBUG("[METHOD] GOOD: " + field.getName() + " CLASS: " + clazz.getName());
					}
				} catch (IllegalAccessException e) {
					// Skip inaccessible fields
					continue;
				} catch (Exception e) {
					// Skip problematic fields but log them
					table.set(field.getName() + "_error", e.getClass().getSimpleName());
					DEBUG("[METHOD] BAD: " + field.getName() + " CLASS: " + clazz.getName());
				}
			}

			return table;

		} catch (Exception e) {
			visited.remove(obj);
			throw new LuaError("Unsafe serialization failed for " + clazz.getName() + ": " + e.getMessage());
		}
	}

	/**
	 * Converts Lua table back to Java object using reflection. WARNING: This
	 * bypasses @LuaField annotations and may set internal state. Use only for
	 * classes you don't control (Minecraft, libraries, etc.)
	 */
	public static <T> T unsafeLuaToJava(LuaValue value, Class<T> targetType) {
		return unsafeLuaToJava(value, targetType, new HashSet<>());
	}

	@SuppressWarnings("unchecked")
	private static <T> T unsafeLuaToJava(LuaValue value, Class<T> targetType, Set<LuaTable> visited) {
		// Try normal conversion first
		try {
			return (T) luaToJava(value, targetType, visited);
		} catch (IllegalArgumentException e) {
			// Not a standard type, use reflection
		}

		if (!value.istable()) {
			throw new LuaError("Expected table for unsafe deserialization of " + targetType.getName());
		}

		LuaTable table = value.checktable();

		// Check for circular reference
		if (visited.contains(table)) {
			throw new LuaError("Circular reference detected during unsafe deserialization");
		}
		visited.add(table);

		try {
			// Determine actual class to instantiate
			Class<?> actualType = targetType;
			LuaValue classValue = table.get("__javaClass");
			if (classValue.isstring() && (targetType.isInterface() || Modifier.isAbstract(targetType.getModifiers()))) {
				try {
					actualType = Class.forName(classValue.checkjstring());
				} catch (ClassNotFoundException ex) {
					throw new LuaError("Unknown class: " + classValue.checkjstring());
				}
			}

			// Create instance
			Constructor<?> constructor = findBestConstructor(actualType);
			constructor.setAccessible(true);

			Object instance;
			if (constructor.getParameterCount() == 0) {
				instance = constructor.newInstance();
			} else {
				// Try to create with nulls/defaults for parameterized constructors
				Object[] params = new Object[constructor.getParameterCount()];
				Class<?>[] paramTypes = constructor.getParameterTypes();
				for (int i = 0; i < params.length; i++) {
					params[i] = getDefaultValue(paramTypes[i]);
				}
				instance = constructor.newInstance(params);
			}

			// Populate all accessible fields
			for (Field field : getAllFields(actualType)) {
				// Skip static and synthetic fields
				if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
					continue;
				}

				// Skip final fields (might cause issues)
				if (Modifier.isFinal(field.getModifiers())) {
					continue;
				}

				LuaValue luaVal = table.get(field.getName());
				if (!luaVal.isnil()) {
					try {
						field.setAccessible(true);
						Object javaVal = unsafeLuaToJavaField(luaVal, field.getType(), visited);
						field.set(instance, javaVal);
					} catch (Exception e) {
						// Skip problematic fields
						continue;
					}
				}
			}

			visited.remove(table);
			return (T) instance;

		} catch (Exception e) {
			throw new LuaError("Unsafe deserialization failed for " + targetType.getName() + ": "
					+ e.getClass().getSimpleName() + " - " + e.getMessage());
		}
	}

	/**
	 * Helper for unsafe field deserialization
	 */
	private static Object unsafeLuaToJavaField(LuaValue value, Class<?> fieldType, Set<LuaTable> visited) {
		// Try normal conversion first
		try {
			return luaToJava(value, fieldType, visited);
		} catch (IllegalArgumentException e) {
			// Fallback to unsafe conversion
			if (value.istable()) {
				return unsafeLuaToJava(value, fieldType, visited);
			}
			throw e;
		}
	}

	/**
	 * Get all fields including inherited ones
	 */
	private static List<Field> getAllFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<>();
		Class<?> current = clazz;

		while (current != null && current != Object.class) {
			fields.addAll(Arrays.asList(current.getDeclaredFields()));
			current = current.getSuperclass();
		}

		return fields;
	}

	/**
	 * Find the best constructor (prefer no-arg, then shortest param list)
	 */
	private static Constructor<?> findBestConstructor(Class<?> clazz) throws NoSuchMethodException {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();

		// Try no-arg first
		try {
			return clazz.getDeclaredConstructor();
		} catch (NoSuchMethodException e) {
			// Find constructor with fewest parameters
			Constructor<?> best = null;
			int minParams = Integer.MAX_VALUE;

			for (Constructor<?> c : constructors) {
				if (c.getParameterCount() < minParams) {
					minParams = c.getParameterCount();
					best = c;
				}
			}

			if (best != null) {
				return best;
			}

			throw new NoSuchMethodException("No suitable constructor found for " + clazz.getName());
		}
	}

	/**
	 * Get default value for a primitive type (for constructor parameters)
	 */
	private static Object getDefaultValue(Class<?> type) {
		if (type == boolean.class)
			return false;
		if (type == byte.class)
			return (byte) 0;
		if (type == short.class)
			return (short) 0;
		if (type == int.class)
			return 0;
		if (type == long.class)
			return 0L;
		if (type == float.class)
			return 0.0f;
		if (type == double.class)
			return 0.0;
		if (type == char.class)
			return '\0';
		return null; // For reference types
	}

	/**
	 * Get cached field information for a class (including inherited fields)
	 */
	private static List<FieldInfo> getCachedFields(Class<?> clazz) {
		return FIELD_CACHE.computeIfAbsent(clazz, c -> {
			List<FieldInfo> fields = new ArrayList<>();
			Class<?> current = c;

			// Walk up the inheritance chain
			while (current != null && current != Object.class) {
				for (Field field : current.getDeclaredFields()) {
					if (field.isAnnotationPresent(LuaField.class)) {
						field.setAccessible(true);

						String name = field.getAnnotation(LuaField.class).value();
						if (name.isEmpty())
							name = field.getName();

						fields.add(new FieldInfo(field, name));
					}
				}
				current = current.getSuperclass();
			}

			return List.copyOf(fields); // Immutable
		});
	}

	/**
	 * Adds all methods and fields of an object to a LuaTable
	 */
	public static LuaTable javaClassTable(Object obj) {
		LuaTable table = new LuaTable();

		// Add accessible fields
		for (Field field : obj.getClass().getDeclaredFields()) {
			if (!field.canAccess(obj))
				continue;

			try {
				LuaValue luaValue = javaToLua(field.get(obj));
				table.set(field.getName(), luaValue);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Failed to access field " + field.getName() + ": " + e.getMessage());
			} catch (IllegalArgumentException e) {
				// Skip unsupported types
				continue;
			}
		}

		// Add methods
		for (Method method : obj.getClass().getMethods()) {
			// Skip weird / synthetic methods
			if (method.isSynthetic() || method.isBridge())
				continue;

			int paramCount = method.getParameterCount();
			LibFunction function = createFunctionWrapper(obj, method, paramCount);

			if (function != null) {
				table.set(method.getName(), function);
			}
		}

		return table;
	}

	/**
	 * Creates appropriate LibFunction wrapper based on parameter count
	 */
	private static LibFunction createFunctionWrapper(Object obj, Method method, int paramCount) {
		return switch (paramCount) {
			case 0 -> new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					try {
						Object result = method.invoke(obj);
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 1 -> new OneArgFunction() {
				@Override
				public LuaValue call(LuaValue a1) {
					try {
						Object p1 = luaToJava(a1, method.getParameterTypes()[0]);
						Object result = method.invoke(obj, p1);
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 2 -> new TwoArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 3 -> new ThreeArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]),
								luaToJava(a3, p[2]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 4 -> new FourArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]),
								luaToJava(a3, p[2]), luaToJava(a4, p[3]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 5 -> new FiveArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4, LuaValue a5) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]),
								luaToJava(a3, p[2]), luaToJava(a4, p[3]), luaToJava(a5, p[4]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 6 -> new SixArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4, LuaValue a5, LuaValue a6) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]),
								luaToJava(a3, p[2]), luaToJava(a4, p[3]), luaToJava(a5, p[4]), luaToJava(a6, p[5]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 7 -> new SevenArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4, LuaValue a5, LuaValue a6,
						LuaValue a7) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]),
								luaToJava(a3, p[2]), luaToJava(a4, p[3]), luaToJava(a5, p[4]), luaToJava(a6, p[5]),
								luaToJava(a7, p[6]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 8 -> new EightArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4, LuaValue a5, LuaValue a6,
						LuaValue a7, LuaValue a8) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]),
								luaToJava(a3, p[2]), luaToJava(a4, p[3]), luaToJava(a5, p[4]), luaToJava(a6, p[5]),
								luaToJava(a7, p[6]), luaToJava(a8, p[7]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 9 -> new NineArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4, LuaValue a5, LuaValue a6,
						LuaValue a7, LuaValue a8, LuaValue a9) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]),
								luaToJava(a3, p[2]), luaToJava(a4, p[3]), luaToJava(a5, p[4]), luaToJava(a6, p[5]),
								luaToJava(a7, p[6]), luaToJava(a8, p[7]), luaToJava(a9, p[8]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			case 10 -> new TenArgFunction() {
				@Override
				public LuaValue call(LuaValue a1, LuaValue a2, LuaValue a3, LuaValue a4, LuaValue a5, LuaValue a6,
						LuaValue a7, LuaValue a8, LuaValue a9, LuaValue a10) {
					try {
						Class<?>[] p = method.getParameterTypes();
						Object result = method.invoke(obj, luaToJava(a1, p[0]), luaToJava(a2, p[1]),
								luaToJava(a3, p[2]), luaToJava(a4, p[3]), luaToJava(a5, p[4]), luaToJava(a6, p[5]),
								luaToJava(a7, p[6]), luaToJava(a8, p[7]), luaToJava(a9, p[8]), luaToJava(a10, p[9]));
						return javaToLua(result);
					} catch (Throwable t) {
						throw new LuaError("Error calling " + method.getName() + ": " + t.getClass().getSimpleName()
								+ " - " + t.getMessage());
					}
				}
			};

			default -> null; // Methods with 11+ parameters not supported
		};
	}
}