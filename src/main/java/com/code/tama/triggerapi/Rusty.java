/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi;

public class Rusty {
	static {
		NativeLoader.load("tts_native");
	}

	public static native int add(int a, int b);
	public static native String processString(String input);
	public static native double readPlayerHealth(Object player);
	public static native void applyDamage(Object entity, float amount, String source);
}