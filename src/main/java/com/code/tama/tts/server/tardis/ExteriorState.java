/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis;

/**
 * LANDING: Is landing, LANDED: Is currently landed, TAKEOFF: Is NOT fully dematerialized, SHOULDNTEXIST: It's fully taken off and shouldn't exist anymore
 */
public enum ExteriorState {
	LANDING, LANDED, TAKINGOFF, SHOULDNTEXIST;
}