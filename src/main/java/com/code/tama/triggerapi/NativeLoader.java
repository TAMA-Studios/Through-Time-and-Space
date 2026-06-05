/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NativeLoader {
	public static void load(String libName) {
		String os = System.getProperty("os.name").toLowerCase();
		String arch = System.getProperty("os.arch").toLowerCase();

		String folder = os.contains("win") ? "windows" : os.contains("mac") ? "macos" : "linux";
		String filename = os.contains("win")
				? libName + ".dll"
				: os.contains("mac") ? "lib" + libName + ".dylib" : "lib" + libName + ".so";

		// Extract from jar to a temp file, then load it
		String resourcePath = "/natives/" + folder + "/" + filename;
		try (InputStream in = NativeLoader.class.getResourceAsStream(resourcePath)) {
			Path tmp = Files.createTempFile(libName, filename);
			Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);
			System.load(tmp.toAbsolutePath().toString());
		} catch (Exception e) {
			throw new RuntimeException("Failed to load native library: " + resourcePath, e);
		}
	}
}