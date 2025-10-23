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
		return getMaxSolder() - getSolder();
	}

	/**
	 * Sets the maximum solder value.
	 *
	 * @param maxSolder
	 *            the maximum solder capacity
	 */
	void setMaxSolder(int maxSolder);

	/**
	 * Sets the current solder value.
	 *
	 * @param solder
	 *            the current solder amount
	 */
	void setSolder(int solder);
}
