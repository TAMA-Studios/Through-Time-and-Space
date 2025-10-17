/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

public class StringUtils {
	public static String capitalize(String str) {
		if (str == null || str.isEmpty())
			return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}

	public static String join(String[] array, String delimiter) {
		if (array == null || array.length == 0)
			return "";
		return String.join(delimiter, array);
	}

	public static String truncate(String str, int maxLength) {
		if (str == null)
			return null;
		if (str.length() <= maxLength)
			return str;
		return str.substring(0, maxLength - 3) + "...";
	}
}
