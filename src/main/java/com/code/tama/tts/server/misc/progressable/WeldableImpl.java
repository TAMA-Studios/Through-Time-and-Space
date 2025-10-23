/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.progressable;

public class WeldableImpl implements IWeldable {
	private int maxWeld;
	private int weld;

	public WeldableImpl(int maxWeld, int weld) {
		this.maxWeld = maxWeld;
		this.weld = weld;
	}

	public WeldableImpl() {
		this(0, 0);
	}

	@Override
	public int getMaxWeld() {
		return maxWeld;
	}

	@Override
	public int getWeld() {
		return weld;
	}

	@Override
	public void setMaxWeld(int maxWeld) {
		this.maxWeld = maxWeld;
	}

	@Override
	public void setWeld(int weld) {
		this.weld = weld;
	}

	@Override
	public String toString() {
		return "WeldableImpl{" + "maxWeld=" + maxWeld + ", weld=" + weld + ", progress=" + getWeldProgress() + '}';
	}
}
