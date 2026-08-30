/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.tardis;

/**
 * LANDING: Is landing, LANDED: Is currently landed, TAKEOFF: Is NOT fully
 * dematerialized, SHOULDNTEXIST: It's fully taken off and shouldn't exist
 * anymore, SPATIALFLIGHT: The exterior has been converted into a rideable
 * {@link com.code.tama.tts.core.entities.TardisFlightEntity} and is being
 * piloted around in real space.
 *
 * <p>
 * Note: while a TARDIS is in SPATIALFLIGHT, the {@code ExteriorTile} itself has
 * been removed from the world (block + BE), so nothing actually holds this
 * value live during the flight - it's set on the tile briefly on
 * takeoff/landing and is mostly here as a hook for other systems (controls,
 * door interaction, etc.) that want to check "is this TARDIS currently doing
 * spatial flight" via {@code itardisLevel.GetExteriorTile() == null}.
 * </p>
 */
public enum ExteriorState {
	LANDING, LANDED, TAKINGOFF, SHOULDNTEXIST, SPATIALFLIGHT;
}