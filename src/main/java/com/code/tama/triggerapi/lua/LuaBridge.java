/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

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

	public static boolean isUnsafe(LuaValue value) {
		if (value.istable()) {
			if (value.checktable().get("__unsafe").isnil())
				return false;
			else
				return value.checktable().get("__unsafe").checkboolean();
		}
		return false;
	}

	/**
	 * Converts a Lua value to a Java object of the specified type
	 */
	private static Object luaToJava(LuaValue value, Class<?> targetType) {
		return isUnsafe(value) ? unsafeLuaToJava(value, targetType) : luaToJava(value, targetType, new HashSet<>());
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
				Object instance = createInstance(actualType, table);

				if (instance == null) {
					throw new LuaError("Cannot create instance of " + actualType.getName()
							+ " - no suitable constructor or factory method found");
				}

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

	private static final int MAX_UNSAFE_DEPTH = 3; // Prevent deep recursion

	// Blacklist of field names to skip (common internal fields)
	private static final Set<String> UNSAFE_FIELD_BLACKLIST = Set.of("ENUM$VALUES", "$VALUES", // Enum internals
			"serialVersionUID", "serialPersistentFields", // Serialization internals
			"class", "$jacocoData", // Class metadata and code coverage
			"table", "modCount", "threshold", "loadFactor", // HashMap/ArrayList internals
			"elementData", "size", "elements", // Collection internals
			"cachedConstructor", "reflectionData", "annotationData", // Reflection cache
			"declaredFields", "publicFields", "declaredMethods", "publicMethods", // More reflection
			"name", "ordinal", "module", "classLoader" // Enum/Class internals
	);

	/**
	 * Converts ANY Java object to Lua, using reflection on all accessible fields.
	 * WARNING: This bypasses @LuaField annotations and may expose internal state.
	 * Use only for classes you don't control (Minecraft, libraries, etc.)
	 */
	public static LuaValue unsafeJavaToLua(Object obj) {
		return unsafeJavaToLua(obj, new IdentityHashMap<>(), 0);
	}

	private static LuaValue unsafeJavaToLua(Object obj, IdentityHashMap<Object, LuaValue> visited, int depth) {
		if (obj == null)
			return LuaValue.NIL;

		// Depth limit to prevent excessive recursion
		if (depth > MAX_UNSAFE_DEPTH) {
			return LuaValue.valueOf(obj.getClass().getSimpleName() + "@" + Integer.toHexString(obj.hashCode()));
		}

		// Try normal conversion first
		try {
			return javaToLua(obj, visited);
		} catch (IllegalArgumentException e) {
			// Not a standard type, use reflection
		}

		// Check for circular reference
		if (visited.containsKey(obj)) {
			return LuaValue.valueOf("[CIRCULAR:" + obj.getClass().getSimpleName() + "]");
		}

		Class<?> clazz = obj.getClass();

		// Skip Java/JDK internal classes that might cause issues
		if (clazz.getName().startsWith("java.lang.reflect") || clazz.getName().startsWith("java.lang.Class")
				|| clazz.getName().startsWith("java.lang.Module") || clazz.getName().startsWith("sun.")
				|| clazz.getName().startsWith("jdk.")) {
			return LuaValue.valueOf(obj.toString());
		}

		// Skip internal Java collection implementations
		if (clazz.getName().startsWith("java.util.")
				&& (clazz.getName().contains("$") || clazz.getName().contains("Immutable"))) {
			// For collections, try to convert to simple list/map
			try {
				if (obj instanceof List<?> list) {
					return listToLuaTable(list, visited);
				}
				if (obj instanceof Map<?, ?> map) {
					return mapToLuaTable(map, visited);
				}
				if (obj instanceof Set<?> set) {
					LuaTable table = new LuaTable();
					int i = 1;
					for (Object item : set) {
						table.set(i++, javaToLua(item, visited));
					}
					return table;
				}
			} catch (Exception e) {
				// Fall through to toString
			}
			return LuaValue.valueOf(obj.toString());
		}

		LuaTable table = new LuaTable();
		visited.put(obj, table);

		try {
			// Add class tag
			table.set("__javaClass", clazz.getName());
			table.set("__unsafe", LuaValue.valueOf(true));

			// Serialize all accessible fields
			for (Field field : getAllFields(clazz)) {
				// Skip static, synthetic, and blacklisted fields
				if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()
						|| UNSAFE_FIELD_BLACKLIST.contains(field.getName())) {
					continue;
				}

				try {
					field.setAccessible(true);
					Object value = field.get(obj);

					// Skip null values to reduce clutter
					if (value != null) {
						// Recursively serialize with increased depth
						table.set(field.getName(), unsafeJavaToLua(value, visited, depth + 1));
					}
				} catch (IllegalAccessException e) {
					// Skip inaccessible fields
					continue;
				} catch (Exception e) {
					// Skip problematic fields silently
					continue;
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

			// Create instance using multiple strategies
			Object instance = createInstance(actualType, table);

			if (instance == null) {
				throw new LuaError("Cannot create instance of " + actualType.getName()
						+ " - no suitable constructor or factory method found");
			}

			// Populate all accessible fields (including final ones!)
			for (Field field : getAllFields(actualType)) {
				// Skip static and synthetic fields
				if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
					continue;
				}

				LuaValue luaVal = table.get(field.getName());
				if (!luaVal.isnil()) {
					try {
						// Force accessibility even for final fields
						setFieldValue(instance, field, luaVal, visited);
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
	 * Try multiple strategies to create an instance of a class
	 */
	private static Object createInstance(Class<?> clazz, LuaTable table) {
		// 1. Handle Records (Java 17 specific)
		if (clazz.isRecord()) {
			return tryRecordConstructor(clazz, table);
		}

		// 2. Try static factory methods (already in your code, but add more names)
		Object factoryResult = tryFactoryMethods(clazz, table);
		if (factoryResult != null)
			return factoryResult;

		// 3. Try no-arg constructor (highest priority for standard objects)
		try {
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (Exception ignored) {
		}

		// 4. Positional/Type-based constructor matching
		Object constructorResult = tryConstructorWithParams(clazz, table);
		if (constructorResult != null)
			return constructorResult;

		// 5. Last Resort: Unsafe (Only works if JVM flags allow or if on custom
		// launcher)
		try {
			sun.misc.Unsafe unsafe = getUnsafe();
			if (unsafe != null)
				return unsafe.allocateInstance(clazz);
		} catch (Exception ignored) {
		}

		return null;
	}

	private static Object tryRecordConstructor(Class<?> clazz, LuaTable table) {
		RecordComponent[] components = clazz.getRecordComponents();
		Class<?>[] paramTypes = new Class<?>[components.length];
		Object[] args = new Object[components.length];

		try {
			for (int i = 0; i < components.length; i++) {
				paramTypes[i] = components[i].getType();
				// Try lookup by component name, then by index
				LuaValue val = table.get(components[i].getName());
				if (val.isnil())
					val = table.get(i + 1);

				args[i] = luaToJava(val, paramTypes[i], new HashSet<>());
			}
			Constructor<?> constructor = clazz.getDeclaredConstructor(paramTypes);
			constructor.setAccessible(true);
			return constructor.newInstance(args);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Get sun.misc.Unsafe for creating instances without calling constructors
	 */
	private static sun.misc.Unsafe getUnsafe() {
		try {
			Field f = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			f.setAccessible(true);
			return (sun.misc.Unsafe) f.get(null);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Try common factory methods: of(), create(), valueOf(), from(), etc.
	 */
	private static Object tryFactoryMethods(Class<?> clazz, LuaTable table) {
		String[] factoryNames = {"of", "create", "valueOf", "from", "parse", "fromString"};

		for (String methodName : factoryNames) {
			try {
				// Try with no parameters
				Method method = clazz.getMethod(methodName);
				if (Modifier.isStatic(method.getModifiers()) && clazz.isAssignableFrom(method.getReturnType())) {
					return method.invoke(null);
				}
			} catch (Exception e) {
				// Try next method
			}

			// Try with common parameter types
			try {
				// Try with String parameter (e.g., ResourceLocation.of("minecraft:dirt"))
				Method method = clazz.getMethod(methodName, String.class);
				if (Modifier.isStatic(method.getModifiers()) && clazz.isAssignableFrom(method.getReturnType())) {

					// Look for common string fields in the table
					LuaValue nameValue = table.get("name");
					if (!nameValue.isnil() && nameValue.isstring()) {
						return method.invoke(null, nameValue.checkjstring());
					}

					LuaValue idValue = table.get("id");
					if (!idValue.isnil() && idValue.isstring()) {
						return method.invoke(null, idValue.checkjstring());
					}

					LuaValue pathValue = table.get("path");
					if (!pathValue.isnil() && pathValue.isstring()) {
						return method.invoke(null, pathValue.checkjstring());
					}
				}
			} catch (Exception e) {
				// Try next
			}
		}

		return null;
	}

	/**
	 * Try to find constructor parameters from the Lua table
	 */
	private static Object tryConstructorWithParams(Class<?> clazz, LuaTable table) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();

		// Sort by parameter count (prefer simpler constructors)
		Arrays.sort(constructors, Comparator.comparingInt(Constructor::getParameterCount));

		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterCount() == 0)
				continue; // Already tried
			if (constructor.getParameterCount() > 10)
				continue; // Too complex

			try {
				constructor.setAccessible(true);
				Class<?>[] paramTypes = constructor.getParameterTypes();
				Object[] params = new Object[paramTypes.length];

				// Try to extract parameters from table
				boolean allFound = true;
				Parameter[] parameters = constructor.getParameters();

				for (int i = 0; i < paramTypes.length; i++) {
					String paramName = parameters[i].getName();
					LuaValue luaVal = table.get(paramName);

					// FALLBACK 1: If name is 'argN', try to find by common names or index
					if (luaVal.isnil() || paramName.startsWith("arg")) {
						// Try the 1-based index (standard Lua array style)
						luaVal = table.get(i + 1);

						// FALLBACK 2: If still nil, try the common field names helper
						if (luaVal.isnil()) {
							luaVal = tryCommonFieldNames(table, paramTypes[i], i);
						}
					}

					if (!luaVal.isnil()) {
						params[i] = luaToJava(luaVal, paramTypes[i], new HashSet<>());
					} else {
						// Use default value
						params[i] = getDefaultValue(paramTypes[i]);
						if (params[i] == null && paramTypes[i].isPrimitive()) {
							allFound = false;
							break;
						}
					}
				}

				if (allFound || constructor.getParameterCount() <= 3) {
					try {
						return constructor.newInstance(params);
					} catch (Exception e) {
						// Constructor failed, try next
					}
				}
			} catch (Exception e) {
				// Try next constructor
			}
		}

		return null;
	}

	/**
	 * Try common field names based on type and position
	 */
	private static LuaValue tryCommonFieldNames(LuaTable table, Class<?> type, int index) {
		// For primitives, try common names
		if (type == int.class || type == Integer.class) {
			String[] names = {"x", "y", "z", "width", "height", "count", "amount", "size", "value", "id"};
			for (String name : names) {
				LuaValue val = table.get(name);
				if (!val.isnil())
					return val;
			}
		}

		if (type == double.class || type == Double.class || type == float.class || type == Float.class) {
			String[] names = {"x", "y", "z", "value", "amount", "scale"};
			for (String name : names) {
				LuaValue val = table.get(name);
				if (!val.isnil())
					return val;
			}
		}

		if (type == String.class) {
			String[] names = {"name", "id", "key", "value", "text", "message", "path"};
			for (String name : names) {
				LuaValue val = table.get(name);
				if (!val.isnil())
					return val;
			}
		}

		// Try indexed access (for positional constructors)
		LuaValue indexed = table.get(index + 1); // Lua is 1-indexed
		if (!indexed.isnil())
			return indexed;

		return LuaValue.NIL;
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
	 * Sets a field value, even if it's final. Uses multiple strategies to bypass
	 * Java's final field protection.
	 */
	private static void setFieldValue(Object instance, Field field, LuaValue luaValue, Set<LuaTable> visited)
			throws Exception {
		Object javaValue = unsafeLuaToJavaField(luaValue, field.getType(), visited);

		// Strategy 1: Try normal reflection first
		try {
			field.setAccessible(true);
			field.set(instance, javaValue);
			return; // Success!
		} catch (IllegalAccessException e) {
			// Field is final, need more aggressive approach
		}

		// Strategy 2: Remove final modifier via reflection (Java 8-11)
		try {
			field.setAccessible(true);

			// Get the modifiers field from Field class
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);

			// Remove the FINAL flag
			int modifiers = field.getModifiers();
			modifiersField.setInt(field, modifiers & ~Modifier.FINAL);

			// Now set the value
			field.set(instance, javaValue);

			// Restore the FINAL flag (good practice)
			modifiersField.setInt(field, modifiers);
			return; // Success!
		} catch (Exception e) {
			// Strategy 2 failed (Java 12+ removed modifiers field)
		}

		// Strategy 3: Use Unsafe to directly write to memory (Java 12+)
		try {
			sun.misc.Unsafe unsafe = getUnsafe();
			if (unsafe != null) {
				long offset = unsafe.objectFieldOffset(field);

				// Set value based on type
				Class<?> type = field.getType();
				if (type == int.class) {
					unsafe.putInt(instance, offset, (Integer) javaValue);
				} else if (type == long.class) {
					unsafe.putLong(instance, offset, (Long) javaValue);
				} else if (type == double.class) {
					unsafe.putDouble(instance, offset, (Double) javaValue);
				} else if (type == float.class) {
					unsafe.putFloat(instance, offset, (Float) javaValue);
				} else if (type == boolean.class) {
					unsafe.putBoolean(instance, offset, (Boolean) javaValue);
				} else if (type == byte.class) {
					unsafe.putByte(instance, offset, (Byte) javaValue);
				} else if (type == short.class) {
					unsafe.putShort(instance, offset, (Short) javaValue);
				} else if (type == char.class) {
					unsafe.putChar(instance, offset, (Character) javaValue);
				} else {
					// Reference type (Object)
					unsafe.putObject(instance, offset, javaValue);
				}
				return; // Success!
			}
		} catch (Exception e) {
			// Strategy 3 failed
		}

		// All strategies failed - throw exception
		throw new IllegalAccessException(
				"Cannot set final field: " + field.getName() + " in class " + instance.getClass().getName());
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

	// ========== MINECRAFT-SPECIFIC HELPERS ==========

	/**
	 * Helper to create ItemStack from Lua table. Handles the complex final fields
	 * properly.
	 */
	public static Object createItemStack(LuaTable table) {
		try {
			Class<?> itemStackClass = Class.forName("net.minecraft.world.item.ItemStack");
			Class<?> itemClass = Class.forName("net.minecraft.world.item.Item");

			// Get required data from table
			LuaValue itemValue = table.get("item");
			int count = table.get("count").optint(1);

			// Try to get the Item
			Object item = null;
			if (!itemValue.isnil()) {
				if (itemValue.istable()) {
					item = unsafeLuaToJava(itemValue, itemClass, new HashSet<>());
				}
			}

			if (item == null) {
				// Try to get from registry by ID
				LuaValue idValue = table.get("id");
				if (!idValue.isnil() && idValue.isstring()) {
					// Try BuiltInRegistries.ITEM.get(ResourceLocation)
					Class<?> registriesClass = Class.forName("net.minecraft.core.registries.BuiltInRegistries");
					Field itemRegistryField = registriesClass.getField("ITEM");
					Object itemRegistry = itemRegistryField.get(null);

					Class<?> resourceLocationClass = Class.forName("net.minecraft.resources.ResourceLocation");
					Method ofMethod = resourceLocationClass.getMethod("of", String.class);
					Object resourceLocation = ofMethod.invoke(null, idValue.checkjstring());

					Method getMethod = itemRegistry.getClass().getMethod("get", resourceLocationClass);
					item = getMethod.invoke(itemRegistry, resourceLocation);
				}
			}

			if (item == null) {
				throw new LuaError("Cannot create ItemStack: no valid item specified");
			}

			// Create ItemStack via constructor: ItemStack(Item item, int count)
			Constructor<?> constructor = itemStackClass.getConstructor(itemClass, int.class);
			Object stack = constructor.newInstance(item, count);

			// Set additional fields if present
			LuaValue tagValue = table.get("tag");
			if (!tagValue.isnil() && tagValue.istable()) {
				Class<?> compoundTagClass = Class.forName("net.minecraft.nbt.CompoundTag");
				Object tag = unsafeLuaToJava(tagValue, compoundTagClass, new HashSet<>());

				Method setTagMethod = itemStackClass.getMethod("setTag", compoundTagClass);
				setTagMethod.invoke(stack, tag);
			}

			return stack;

		} catch (Exception e) {
			throw new LuaError("Failed to create ItemStack: " + e.getMessage());
		}
	}

	/**
	 * Helper to create BlockPos from Lua table. Much simpler than ItemStack.
	 */
	public static Object createBlockPos(LuaTable table) {
		try {
			int x = table.get("x").optint(0);
			int y = table.get("y").optint(0);
			int z = table.get("z").optint(0);

			Class<?> blockPosClass = Class.forName("net.minecraft.core.BlockPos");
			Constructor<?> constructor = blockPosClass.getConstructor(int.class, int.class, int.class);
			return constructor.newInstance(x, y, z);

		} catch (Exception e) {
			throw new LuaError("Failed to create BlockPos: " + e.getMessage());
		}
	}

	/**
	 * Helper to create ResourceLocation from Lua table or string.
	 */
	public static Object createResourceLocation(Object luaValue) {
		try {
			String path;
			if (luaValue instanceof String s) {
				path = s;
			} else if (luaValue instanceof LuaValue lv && lv.isstring()) {
				path = lv.checkjstring();
			} else if (luaValue instanceof LuaTable table) {
				LuaValue pathValue = table.get("path");
				if (!pathValue.isnil()) {
					path = pathValue.checkjstring();
				} else {
					LuaValue namespaceValue = table.get("namespace");
					LuaValue nameValue = table.get("name");
					if (!namespaceValue.isnil() && !nameValue.isnil()) {
						path = namespaceValue.checkjstring() + ":" + nameValue.checkjstring();
					} else {
						throw new LuaError("ResourceLocation requires 'path' or 'namespace'+'name'");
					}
				}
			} else {
				throw new LuaError("ResourceLocation requires string or table");
			}

			Class<?> resourceLocationClass = Class.forName("net.minecraft.resources.ResourceLocation");
			Method ofMethod = resourceLocationClass.getMethod("of", String.class);
			return ofMethod.invoke(null, path);

		} catch (Exception e) {
			throw new LuaError("Failed to create ResourceLocation: " + e.getMessage());
		}
	}

	/**
	 * SAFER VERSION: Only exposes methods (no fields) from third-party objects.
	 * Recommended for Minecraft objects where you only want to call methods. Does
	 * NOT serialize internal state.
	 */
	public static LuaTable unsafeMethodsOnly(Object obj) {
		LuaTable table = new LuaTable();

		// Add class info
		table.set("__javaClass", obj.getClass().getName());
		table.set("__methodsOnly", LuaValue.valueOf(true));

		// Add all public methods
		for (Method method : obj.getClass().getMethods()) {
			// Skip Object methods and synthetic/bridge methods
			if (method.isSynthetic() || method.isBridge())
				continue;
			if (method.getDeclaringClass() == Object.class)
				continue;

			int paramCount = method.getParameterCount();
			LibFunction function = createFunctionWrapper(obj, method, paramCount, true);

			if (function != null) {
				table.set(method.getName(), function);
			}
		}

		return table;
	}

	/**
	 * COMPLETE UNSAFE: Exposes BOTH fields AND methods from third-party objects.
	 * WARNING: This creates a fully interactive Lua wrapper with both data and
	 * behavior. Use when you need complete access to an object you don't control.
	 */
	public static LuaTable unsafeFieldsAndMethods(Object obj) {
		return unsafeFieldsAndMethods(obj, new IdentityHashMap<>(), 0);
	}

	private static LuaTable unsafeFieldsAndMethods(Object obj, IdentityHashMap<Object, LuaTable> visited, int depth) {
		if (obj == null)
			return null;

		// Depth limit
		if (depth > MAX_UNSAFE_DEPTH) {
			LuaTable table = new LuaTable();
			table.set("__truncated", LuaValue.valueOf(true));
			table.set("__class", obj.getClass().getSimpleName());
			table.set("__toString", obj.toString());
			return table;
		}

		// Check for circular reference
		if (visited.containsKey(obj)) {
			LuaTable circular = new LuaTable();
			circular.set("__circular", LuaValue.valueOf(true));
			circular.set("__class", obj.getClass().getSimpleName());
			return circular;
		}

		Class<?> clazz = obj.getClass();
		LuaTable table = new LuaTable();
		visited.put(obj, table);

		// Add class metadata
		table.set("__unsafe", LuaValue.valueOf(true));
		table.set("__javaClass", clazz.getName());
		table.set("__fieldsAndMethods", LuaValue.valueOf(true));

		// Add all public methods
		for (Method method : clazz.getMethods()) {
			// Skip Object methods and synthetic/bridge methods
			if (method.isSynthetic() || method.isBridge())
				continue;
			if (method.getDeclaringClass() == Object.class)
				continue;

			int paramCount = method.getParameterCount();
			LibFunction function = createFunctionWrapper(obj, method, paramCount, true);

			if (function != null) {
				table.set(method.getName(), function);
			}
		}

		// Add all accessible fields as values (not methods)
		for (Field field : getAllFields(clazz)) {
			// Skip static, synthetic, and blacklisted fields
			if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()
					|| UNSAFE_FIELD_BLACKLIST.contains(field.getName())) {
				continue;
			}

			try {
				field.setAccessible(true);
				Object value = field.get(obj);

				if (value != null) {
					// Convert field value to Lua
					LuaValue luaValue = convertFieldValue(value, visited, depth);
					if (luaValue != null) {
						table.set(field.getName(), luaValue);
					}
				}
			} catch (IllegalAccessException e) {
				// Skip inaccessible fields
				continue;
			} catch (Exception e) {
				// Skip problematic fields silently
				continue;
			}
		}

		visited.remove(obj); // Allow reuse at different depths
		return table;
	}

	/**
	 * Helper to convert field values intelligently during unsafe serialization
	 */
	private static LuaValue convertFieldValue(Object value, IdentityHashMap<Object, LuaTable> visited, int depth) {
		if (value == null)
			return LuaValue.NIL;

		// Primitives and strings
		if (value instanceof Integer i)
			return LuaValue.valueOf(i);
		if (value instanceof Long l)
			return LuaValue.valueOf(l.doubleValue());
		if (value instanceof Double d)
			return LuaValue.valueOf(d);
		if (value instanceof Float f)
			return LuaValue.valueOf(f);
		if (value instanceof Boolean b)
			return LuaValue.valueOf(b);
		if (value instanceof String s)
			return LuaValue.valueOf(s);
		if (value instanceof Byte b)
			return LuaValue.valueOf(b);
		if (value instanceof Short s)
			return LuaValue.valueOf(s);
		if (value instanceof Character c)
			return LuaValue.valueOf(c.toString());

		// Arrays
		if (value.getClass().isArray()) {
			LuaTable table = new LuaTable();
			int length = Array.getLength(value);
			for (int i = 0; i < length && i < 100; i++) { // Limit array size
				Object element = Array.get(value, i);
				LuaValue luaElement = convertFieldValue(element, visited, depth + 1);
				if (luaElement != null) {
					table.set(i + 1, luaElement);
				}
			}
			if (length > 100) {
				table.set("__truncated", LuaValue.valueOf(true));
				table.set("__actualLength", length);
			}
			return table;
		}

		// Collections
		if (value instanceof List<?> list) {
			LuaTable table = new LuaTable();
			int max = Math.min(list.size(), 100); // Limit list size
			for (int i = 0; i < max; i++) {
				LuaValue luaElement = convertFieldValue(list.get(i), visited, depth + 1);
				if (luaElement != null) {
					table.set(i + 1, luaElement);
				}
			}
			if (list.size() > 100) {
				table.set("__truncated", LuaValue.valueOf(true));
				table.set("__actualSize", list.size());
			}
			return table;
		}

		if (value instanceof Map<?, ?> map) {
			LuaTable table = new LuaTable();
			int count = 0;
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				if (count++ > 100) { // Limit map size
					table.set("__truncated", LuaValue.valueOf(true));
					table.set("__actualSize", map.size());
					break;
				}
				try {
					LuaValue key = convertFieldValue(entry.getKey(), visited, depth + 1);
					LuaValue val = convertFieldValue(entry.getValue(), visited, depth + 1);
					if (key != null && val != null) {
						table.set(key, val);
					}
				} catch (Exception e) {
					// Skip problematic entries
				}
			}
			return table;
		}

		if (value instanceof Set<?> set) {
			LuaTable table = new LuaTable();
			int i = 1;
			for (Object item : set) {
				if (i > 100) { // Limit set size
					table.set("__truncated", LuaValue.valueOf(true));
					table.set("__actualSize", set.size());
					break;
				}
				LuaValue luaElement = convertFieldValue(item, visited, depth + 1);
				if (luaElement != null) {
					table.set(i++, luaElement);
				}
			}
			return table;
		}

		// Enums
		if (value instanceof Enum<?> e) {
			return LuaValue.valueOf(e.name());
		}

		Class<?> clazz = value.getClass();

		// Skip problematic Java internals
		if (clazz.getName().startsWith("java.lang.reflect") || clazz.getName().startsWith("java.lang.Class")
				|| clazz.getName().startsWith("java.lang.Module") || clazz.getName().startsWith("sun.")
				|| clazz.getName().startsWith("jdk.")) {
			return LuaValue.valueOf(value.toString());
		}

		// For complex objects, recursively create fields+methods table
		if (depth < MAX_UNSAFE_DEPTH) {
			return unsafeFieldsAndMethods(value, visited, depth + 1);
		}

		// At max depth, just return toString
		return LuaValue.valueOf(value.toString());
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
			LibFunction function = createFunctionWrapper(obj, method, paramCount, false);

			if (function != null) {
				table.set(method.getName(), function);
			}
		}

		return table;
	}

	/**
	 * Creates appropriate LibFunction wrapper based on parameter count
	 */
	private static LibFunction createFunctionWrapper(Object obj, Method method, int paramCount, boolean unSafe) {
		return switch (paramCount) {
			case 0 -> new ZeroArgFunction() {
				@Override
				public LuaValue call() {
					try {
						Object result = method.invoke(obj);
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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
						return !unSafe ? javaToLua(result) : unsafeFieldsAndMethods(result);
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