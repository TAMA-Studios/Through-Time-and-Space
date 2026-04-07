/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method on a {@link LuaSerializable} class to be exposed to Lua
 * scripts as a callable function on the serialized table.
 *
 * <p>
 * Only methods annotated with {@code @LuaMethod} are exposed. This is the safe,
 * opt-in alternative to {@code unsafeFieldsAndMethods}, which exposes every
 * public method via reflection indiscriminately.
 *
 * <p>
 * Supports up to 10 parameters (matching the {@code createFunctionWrapper}
 * limit in {@link LuaBridge}). Methods returning {@code void} return
 * {@code nil} in Lua. Parameter and return types follow the same conversion
 * rules as {@link LuaField} fields — primitives, Strings, and nested
 * {@link LuaSerializable} objects are all handled automatically.
 *
 * <p>
 * <b>Example:</b>
 *
 * <pre>{@code
 * public class PlayerData implements LuaSerializable {
 *
 * 	@LuaField("health")
 * 	public float health = 20.0f;
 *
 * 	@LuaMethod("heal")
 * 	public void heal(float amount) {
 * 		health = Math.min(20.0f, health + amount);
 * 	}
 *
 * 	@LuaMethod("is_alive") // optional snake_case rename
 * 	public boolean isAlive() {
 * 		return health > 0;
 * 	}
 * }
 * }</pre>
 *
 * Then in Lua:
 *
 * <pre>{@code
 *   local pd = ctx.playerData
 *   if pd.is_alive() then
 *       pd.heal(5.0)
 *   end
 * }</pre>
 *
 * @see LuaField
 * @see LuaSerializable
 * @see LuaBridge
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LuaMethod {
	/**
	 * The name used to call this method from Lua. Defaults to the Java method name
	 * if left empty.
	 */
	String value() default "";
}