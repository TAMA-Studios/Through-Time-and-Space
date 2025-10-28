/* (C) TAMA Studios 2025 */
package com.code.tama.tts.server.misc.progressable;

public class SolderableImpl implements ISolderable {
	private int maxSolder;
	private int solder;

	public SolderableImpl(int maxSolder, int solder) {
		this.maxSolder = maxSolder;
		this.solder = solder;
	}

	public SolderableImpl() {
		this(0, 0);
	}

	@Override
	public int getMaxSolder() {
		return maxSolder;
	}

	@Override
	public int getSolder() {
		return solder;
	}

	@Override
	public void setSolder(int solder) {
		this.solder = solder;
	}

	@Override
	public String toString() {
		return String.format("SolderableImpl{maxSolder=%s, solder=%s, progress=%s}", maxSolder, solder,
				getSolderProgress());
	}
}
