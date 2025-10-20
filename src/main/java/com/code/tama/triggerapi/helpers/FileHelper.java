/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.helpers;

import java.io.*;
import java.nio.file.Path;

import net.minecraftforge.fml.loading.FMLPaths;

import com.code.tama.triggerapi.Logger;
import com.code.tama.triggerapi.TriggerAPI;

public class FileHelper {
	private static String getBaseDir() {
		return "TriggerAPI/" + TriggerAPI.MOD_ID + "/stored";
	}

	public static boolean appendToStoredFile(String fileName, String content) {
		Path dirPath = FMLPaths.GAMEDIR.get().resolve(getBaseDir());
		File directory = dirPath.toFile();

		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				Logger.error("Failed to create directory: %s", directory.getAbsolutePath());
				return false;
			}
		}

		File file = new File(directory, fileName + ".txt");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // 'true' enables append mode
			writer.write(content);
			writer.newLine(); // add a newline after each append
			Logger.info("Appended to file: %s", file.getAbsolutePath());
			return true;
		} catch (IOException e) {
			Logger.error("Error appending to file %s: %s", file.getName(), e.getMessage());
			return false;
		}
	}

	public static boolean createStoredFile(String fileName, String content) {
		Path dirPath = FMLPaths.GAMEDIR.get().resolve(getBaseDir());
		File directory = dirPath.toFile();

		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				Logger.error("Failed to create directory: %s", directory.getAbsolutePath());
				return false;
			}
		}

		File file = new File(directory, fileName + ".txt");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(content);
			Logger.info("Created file: %s", file.getAbsolutePath());
			return true;
		} catch (IOException e) {
			Logger.error("Error creating file %s: %s", file.getName(), e.getMessage());
			return false;
		}
	}

	public static String getOrCreateFile(String fileName) {
		if (storedFileExists(fileName))
			return getStoredFile(fileName);
		else
			createStoredFile(fileName, "");
		return "";
	}

	public static boolean getOrCreateFileAndAppend(String fileName, String toAppend) {
		if (!storedFileExists(fileName)) {
			createStoredFile(fileName, "");
		}
		return appendToStoredFile(fileName, toAppend);
	}

	public static String getStoredFile(String fileName) {
		Path filePath = FMLPaths.GAMEDIR.get().resolve(getBaseDir()).resolve(fileName + ".txt");
		File file = filePath.toFile();

		if (!file.exists()) {
			Logger.warn("File not found: %s", file.getAbsolutePath());
			return null;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
			Logger.info("Retrieved file: %s", file.getAbsolutePath());
			return content.toString().trim();
		} catch (IOException e) {
			Logger.error("Error reading file %s: %s", file.getName(), e.getMessage());
			return null;
		}
	}

	public static boolean storedFileExists(String fileName) {
		Path filePath = FMLPaths.GAMEDIR.get().resolve(getBaseDir()).resolve(fileName + ".txt");
		return filePath.toFile().exists();
	}
}
