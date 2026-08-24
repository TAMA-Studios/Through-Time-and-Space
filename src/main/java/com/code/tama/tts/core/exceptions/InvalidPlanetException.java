/* (C) TAMA Studios 2025 */
package com.code.tama.tts.core.exceptions;

import java.util.List;

public class InvalidPlanetException extends RuntimeException {
	List<String> exceptions;

	public InvalidPlanetException(String message) {
		super(message);
	}

	public InvalidPlanetException(List<String> exceptions) {
		this.exceptions = exceptions;
	}

	@Override
	public String getMessage() {
		return this.exceptions != null ? this.exceptions.toString() : super.getMessage();
	}
}
