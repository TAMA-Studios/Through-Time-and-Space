/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.progressable;

public interface ISolderable {

	/**
	 * @return The maximum amount of solder possible.
	 */
	int getMaxSolder();

	/**
	 * @return The current amount of solder.
	 */
	int getSolder();

	/**
	 * @return The remaining solder progress (max - current).
	 */
	default int getSolderProgress() {
		if (getMaxSolder() <= 0) return 0; // avoid division by zero
		float progress = (getSolder() / (float) getMaxSolder()) * 100f;
		return Math.min(100, Math.max(0, Math.round(progress)));
	}

	/**
	 * Sets the current solder value.
	 *
	 * @param solder
	 *            the current solder amount
	 */
	void setSolder(int solder);
}
