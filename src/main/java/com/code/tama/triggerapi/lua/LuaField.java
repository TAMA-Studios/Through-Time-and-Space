/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.lua;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface LuaField {
	String value() default ""; // Optional custom name
}