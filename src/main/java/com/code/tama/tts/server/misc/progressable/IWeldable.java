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
		return getMaxWeld() - getWeld();
	}

	/**
	 * Sets the maximum weld value.
	 *
	 * @param maxWeld
	 *            the maximum weld capacity
	 */
	void setMaxWeld(int maxWeld);

	/**
	 * Sets the current weld value.
	 *
	 * @param weld
	 *            the current weld amount
	 */
	void setWeld(int weld);
}
