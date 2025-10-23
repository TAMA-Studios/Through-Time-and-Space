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
		return getMaxBrazing() - getBrazing();
	}

	/**
	 * Sets the maximum brazing value.
	 *
	 * @param maxBrazing
	 *            the maximum brazing capacity
	 */
	void setMaxBrazing(int maxBrazing);

	/**
	 * Sets the current brazing value.
	 *
	 * @param brazing
	 *            the current brazing amount
	 */
	void setBrazing(int brazing);
}
