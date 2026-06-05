/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import net.minecraftforge.fml.loading.FMLPaths;

import com.code.tama.triggerapi.Logger;
import com.code.tama.triggerapi.NativeLoader;
import com.code.tama.triggerapi.TriggerAPI;

/**
 * File I/O helper. Java resolves Forge/mod paths; Rust performs all actual file
 * operations with buffered I/O (64 KB buffers, no charset boxing overhead).
 */
public class FileHelper {

	static {
		NativeLoader.load("tts_native");
	}

	// -- Native (Rust) --------------------------------------------------------

	/** Creates or overwrites a file with the given content. */
	private static native boolean writeFile(String absolutePath, String content);

	/** Appends content + newline to a file, creating it if absent. */
	private static native boolean appendFile(String absolutePath, String content);

	/** Reads a file and returns its content trimmed, or null on failure. */
	private static native String readFile(String absolutePath);

	/** Returns true if the file exists. */
	private static native boolean fileExists(String absolutePath);

	/** Deletes a file. Returns true on success. */
	private static native boolean deleteFile(String absolutePath);

	/** Creates a directory and all parents. Returns true on success. */
	private static native boolean ensureDirExists(String absoluteDirPath);

	/**
	 * Appends multiple lines at once in a single buffered write. Pass lines joined
	 * with '\n' — far faster than looping appendFile().
	 */
	private static native boolean appendLines(String absolutePath, String lines);

	/** Copies src to dest, creating parent directories of dest as needed. */
	private static native boolean copyFile(String srcPath, String destPath);

	/** Returns file size in bytes, or -1 if the file doesn't exist. */
	private static native long getFileSize(String absolutePath);

	/**
	 * Lists filenames (not full paths) in a directory, newline-separated. Returns
	 * null if the directory doesn't exist.
	 */
	private static native String listFiles(String absoluteDirPath);

	/** Wipes a file's content without deleting it. */
	private static native boolean truncateFile(String absolutePath);

	// -- Path helpers ---------------------------------------------------------

	private static String getBaseDir() {
		return "TriggerAPI/" + TriggerAPI.MOD_ID + "/stored";
	}

	/** Resolves a stored-file path under the standard mod directory. */
	private static String storedPath(String fileName) {
		return FMLPaths.GAMEDIR.get().resolve(getBaseDir()).resolve(fileName + ".txt").toAbsolutePath().toString();
	}

	/** Resolves a stored-file path under a custom subdirectory. */
	private static String customPath(String subPath, String fileName) {
		return FMLPaths.GAMEDIR.get().resolve(subPath).resolve(fileName + ".txt").toAbsolutePath().toString();
	}

	/** The absolute path of the standard stored-files directory. */
	private static String storedDir() {
		return FMLPaths.GAMEDIR.get().resolve(getBaseDir()).toAbsolutePath().toString();
	}

	// -- Public API — same signatures as original FileHelper ------------------

	public static boolean appendToStoredFile(String fileName, String content) {
		boolean ok = appendFile(storedPath(fileName), content);
		if (!ok)
			Logger.error("Failed to append to file: %s", fileName);
		return ok;
	}

	public static boolean createStoredFile(String fileName, String content) {
		boolean ok = writeFile(storedPath(fileName), content);
		if (ok)
			Logger.info("Created file: %s", storedPath(fileName));
		else
			Logger.error("Failed to create file: %s", fileName);
		return ok;
	}

	public static boolean createStoredFileCustomPath(String path, String fileName, String content) {
		boolean ok = writeFile(customPath(path, fileName), content);
		if (ok)
			Logger.info("Created file: %s", customPath(path, fileName));
		else
			Logger.error("Failed to create file: %s", fileName);
		return ok;
	}

	public static String getOrCreateFile(String fileName) {
		if (storedFileExists(fileName))
			return getStoredFile(fileName);
		createStoredFile(fileName, "");
		return "";
	}

	public static boolean getOrCreateFileAndAppend(String fileName, String toAppend) {
		// appendFile already creates the file if absent — single native call
		return appendFile(storedPath(fileName), toAppend);
	}

	public static String getStoredFile(String fileName) {
		String content = readFile(storedPath(fileName));
		if (content == null)
			Logger.warn("File not found: %s", storedPath(fileName));
		else
			Logger.info("Retrieved file: %s", storedPath(fileName));
		return content;
	}

	public static boolean storedFileExists(String fileName) {
		return fileExists(storedPath(fileName));
	}

	// -- Extended API (new, not in original) ----------------------------------

	/** Deletes a stored file. */
	public static boolean deleteStoredFile(String fileName) {
		return deleteFile(storedPath(fileName));
	}

	/**
	 * Appends many lines at once in a single buffered write. Faster than calling
	 * appendToStoredFile() in a loop.
	 *
	 * @param lines
	 *            array of lines to append
	 */
	public static boolean appendManyLines(String fileName, String[] lines) {
		return appendLines(storedPath(fileName), String.join("\n", lines));
	}

	/** Copies a stored file to another stored file. */
	public static boolean copyStoredFile(String srcFileName, String destFileName) {
		return copyFile(storedPath(srcFileName), storedPath(destFileName));
	}

	/** Returns the size of a stored file in bytes, or -1 if missing. */
	public static long getStoredFileSize(String fileName) {
		return getFileSize(storedPath(fileName));
	}

	/**
	 * Lists the filenames in the stored-files directory. Returns an empty array if
	 * the directory doesn't exist yet.
	 */
	public static String[] listStoredFiles() {
		String raw = listFiles(storedDir());
		if (raw == null || raw.isEmpty())
			return new String[0];
		return raw.split("\n");
	}

	/** Wipes a stored file's content without deleting it. */
	public static boolean truncateStoredFile(String fileName) {
		return truncateFile(storedPath(fileName));
	}

	// -- Raw-path API (for callers that manage their own paths) ----------------

	/**
	 * Write to any absolute path. Useful when not using the standard stored dir.
	 */
	public static boolean writeRaw(String absolutePath, String content) {
		return writeFile(absolutePath, content);
	}

	public static boolean appendRaw(String absolutePath, String content) {
		return appendFile(absolutePath, content);
	}

	public static String readRaw(String absolutePath) {
		return readFile(absolutePath);
	}

	public static boolean existsRaw(String absolutePath) {
		return fileExists(absolutePath);
	}
}