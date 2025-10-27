/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.progressable;

public class BrazeableImpl implements IBrazeable {
	private int maxBrazing;
	private int brazing;

	public BrazeableImpl(int maxBrazing, int brazing) {
		this.maxBrazing = maxBrazing;
		this.brazing = brazing;
	}

	public BrazeableImpl() {
		this(0, 0);
	}

	@Override
	public int getMaxBrazing() {
		return maxBrazing;
	}

	@Override
	public int getBrazing() {
		return brazing;
	}

	@Override
	public void setBrazing(int brazing) {
		this.brazing = brazing;
	}

	@Override
	public String toString() {
		return "BrazeableImpl{" + "maxBrazing=" + maxBrazing + ", brazing=" + brazing + ", progress="
				+ getBrazingProgress() + '}';
	}
}
