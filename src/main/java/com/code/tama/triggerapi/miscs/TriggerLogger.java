/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi.miscs;

import static com.code.tama.tts.TTSMod.MODID;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** Trigger API Logger V1.0 (Don't use this it's old) * */
@Deprecated
public class TriggerLogger {
	private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final double Version = 1.0;

	private final int bufferSize; // Max entries before flushing

	private final Object lock = new Object(); // For thread safety
	private final List<String> logBuffer; // Buffer to reduce frequent writes
	private final File logFile;

	// Convenience constructor with default buffer size
	public TriggerLogger(String filePath) {
		this(filePath, 100);
	}

	// Constructor: Initialize with file path and buffer size
	public TriggerLogger(String filePath, int bufferSize) {
		this.logFile = new File(filePath);
		this.logBuffer = new ArrayList<>();
		this.bufferSize = bufferSize > 0 ? bufferSize : 5; // Default to 5 if invalid

		// Ensure the parent directory exists
		File parentDir = logFile.getParentFile();
		if (parentDir != null && !parentDir.exists()) {
			parentDir.mkdirs();
		}
	}

	// Example usage in a Minecraft mod context
	public static void main(String[] args) {
		// For testing outside Minecraft, replace it with mod integration
		TriggerLogger logger = new TriggerLogger("log.txt");
		logger.log("Trigger Logger V{} Started for mod {}", Version, MODID);
		logger.flush(); // Force write to file
	}

	// Private method to write buffer to file
	private void flushToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
			for (String entry : logBuffer) {
				writer.write(entry);
				writer.newLine();
			}
			logBuffer.clear();
		} catch (IOException e) {
			System.err.println("Failed to write to log file: " + e.getMessage());
			// Keep buffer intact to retry later if desired
		}
	}

	// Flush buffer to file manually (e.g., on mod disable)
	public void flush() {
		synchronized (lock) {
			if (!logBuffer.isEmpty()) {
				flushToFile();
			}
		}
	}

	// Log a message with timestamp
	public void log(String message) {
		String formattedMessage = String.format("[%s] %s", TIMESTAMP_FORMAT.format(LocalDateTime.now()), message);

		synchronized (lock) { // Thread-safe buffer access
			logBuffer.add(formattedMessage);
			if (logBuffer.size() >= bufferSize) {
				flushToFile();
			}
		}
	}

	// Log a message with timestamp
	public void log(String message, Object... objects) {
		String messageToSend = message;
		for (Object object : objects) {
			messageToSend = messageToSend.replaceFirst("[{}]", String.valueOf(object));
			messageToSend = messageToSend.replaceFirst("[}]", "");
		}
		message = messageToSend;
		String formattedMessage = String.format("[%s] %s", TIMESTAMP_FORMAT.format(LocalDateTime.now()), message);

		synchronized (lock) { // Thread-safe buffer access
			logBuffer.add(formattedMessage);
			if (logBuffer.size() >= bufferSize) {
				flushToFile();
			}
		}
	}
}
