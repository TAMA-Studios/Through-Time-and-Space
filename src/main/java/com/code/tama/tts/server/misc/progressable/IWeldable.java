/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.progressable;

public interface IWeldable {

	/**
	 * @return The maximum amount of weld possible.
	 */
	int getMaxWeld();

	/**
	 * @return The current amount of weld.
	 */
	int getWeld();

	/**
	 * @return The remaining weld progress (max - current).
	 */
	default int getWeldProgress() {
		if (getWeld() <= 0)
			return 0; // avoid division by zero
		float progress = (getWeld() / (float) getMaxWeld()) * 100f;
		return Math.min(100, Math.max(0, Math.round(progress)));
	}

	/**
	 * Sets the current weld value.
	 *
	 * @param weld
	 *            the current weld amount
	 */
	void setWeld(int weld);
}
