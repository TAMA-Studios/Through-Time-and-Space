/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

public class UVUtils {
	/**
	 * Returns the U/V coordinate for the given pixel for the given image size
	 *
	 * @param pix
	 *            the pixel coordinate
	 * @param imgSize
	 *            the size of the image on the same axis as <code>pix</code>. if
	 *            <code>pix</code> is on the X axis, pass the images X size.
	 * @return the calculated U/V coordinate
	 */
	public static float PixelToCoord(int pix, int imgSize) {
		return (float) pix / imgSize;
	}
}
