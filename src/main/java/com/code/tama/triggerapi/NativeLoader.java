/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class NativeLoader {
	private static boolean loaded = false;

	public static synchronized void load(String libName) {
		if (loaded)
			return;

		String os = System.getProperty("os.name").toLowerCase();

		String folder = os.contains("win") ? "windows" : os.contains("mac") ? "macos" : "linux";
		String filename = os.contains("win")
				? libName + ".dll"
				: os.contains("mac") ? "lib" + libName + ".dylib" : "lib" + libName + ".so";

		String resourcePath = "/natives/" + folder + "/" + filename;

		// Forge's classloader won't respond to Class.getResourceAsStream so walk up the
		// classloader chain until something actually finds the resource
		InputStream in = null;
		for (ClassLoader cl : new ClassLoader[]{Thread.currentThread().getContextClassLoader(),
				NativeLoader.class.getClassLoader(), ClassLoader.getSystemClassLoader()}) {
			if (cl == null)
				continue;
			in = cl.getResourceAsStream(resourcePath.substring(1)); // strip leading /
			if (in == null)
				in = cl.getResourceAsStream(resourcePath); // try with leading /
			if (in != null)
				break;
		}

		if (in == null) {
			throw new RuntimeException("Native library not found in jar: " + resourcePath
					+ " - make sure the jar was built with copyNativeLibs");
		}

		try (InputStream stream = in) {
			// Write to a temp file cause we can't load directly from inside a jar
			Path tmp = Files.createTempFile(libName + "-", filename);
			tmp.toFile().deleteOnExit();
			Files.copy(stream, tmp, StandardCopyOption.REPLACE_EXISTING);
			System.load(tmp.toAbsolutePath().toString());
			loaded = true;
		} catch (Exception e) {
			throw new RuntimeException("Failed to extract/load native library: " + resourcePath, e);
		}
	}
}