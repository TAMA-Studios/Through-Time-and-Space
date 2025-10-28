/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.progressable;

public interface IBrazeable {

	/**
	 * @return The maximum amount of brazing possible.
	 */
	int getMaxBrazing();

	/**
	 * @return The current amount of brazing.
	 */
	int getBrazing();

	/**
	 * @return The remaining brazing progress (max - current).
	 */
	default int getBrazingProgress() {

		if (getMaxBrazing() <= 0)
			return 0; // avoid division by zero
		float progress = (getBrazing() / (float) getMaxBrazing()) * 100f;
		return Math.min(100, Math.max(0, Math.round(progress)));
	}

	/**
	 * Sets the current brazing value.
	 *
	 * @param brazing
	 *            the current brazing amount
	 */
	void setBrazing(int brazing);
}
