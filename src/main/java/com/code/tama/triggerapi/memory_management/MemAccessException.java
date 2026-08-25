/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.memory_management;

public class MemAccessException extends Exception {
	public MemAccessException(String message) {
		super("MemoryAccessException: " + message);
	}
}
