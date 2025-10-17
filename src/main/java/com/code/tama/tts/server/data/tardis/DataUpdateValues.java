/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.data.tardis;

/**
 * Holds the constants used to define which data is sent in the TARDIS client
 * update packet *
 */
public class DataUpdateValues {
	/** Update EVERYTHING * */
	public static final int ALL = 0x0;

	/**
	 * General Data, stuff like light levels, disco mode, door data, subsystems, etc
	 * *
	 */
	public static final int DATA = 0x3;

	/** Update only Flight data (is in flight, ticks in flight, etc) * */
	public static final int FLIGHT = 0x2;

	/** Update only Navigational data (Mainly destination/location data) * */
	public static final int NAVIGATIONAL = 0x1;

	/**
	 * Update only data required for rendering, mainly the exterior model data,
	 * vortex name, etc *
	 */
	public static final int RENDERING = 0x4;
}
