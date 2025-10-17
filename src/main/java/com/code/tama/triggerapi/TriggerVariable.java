/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface TriggerVariable {
	String value() default "default";
}
