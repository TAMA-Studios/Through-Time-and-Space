/* (C) TAMA Studios 2025 */
package com.code.tama.triggerapi;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.code.tama.tts.TTSMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;

import net.minecraftforge.fml.loading.FMLPaths;

public class Logger {
	public static final DateTimeFormatter DATE_FORMAT_FILE = DateTimeFormatter.ofPattern("HH-mm");
	public static final DateTimeFormatter DATE_FORMAT_FOLDER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(TriggerAPI.getModId());
	public static final String LOG_DIR = "TriggerAPI/" + TriggerAPI.MOD_ID + "/logs/";

	static {
		setupFileLogging();
	}

	private static void setupFileLogging() {

		String FileTimestamp = LocalDateTime.now().format(DATE_FORMAT_FILE);
		String FolderTimestamp = LocalDateTime.now().format(DATE_FORMAT_FOLDER);
		String fileName = String.format("%s.txt", FileTimestamp);

		Path logPath = FMLPaths.GAMEDIR.get().resolve(LOG_DIR + FolderTimestamp);
		File logDir = logPath.toFile();

		if (!logDir.exists())
			logDir.mkdirs();

		String filePath = logPath.resolve(fileName).toString();

		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		Configuration config = context.getConfiguration();

		PatternLayout layout = PatternLayout.newBuilder().withPattern("%d{yyyy-MM-dd HH:mm:ss} [%t] %-5level: %msg%n")
				.build();

		Appender fileAppender = FileAppender.newBuilder().setName("TriggerAPIFileAppender").withFileName(filePath)
				.setLayout(layout).build();

		fileAppender.start();
		config.addLoggerAppender((org.apache.logging.log4j.core.Logger) LOGGER, fileAppender);
		context.updateLoggers();
	}

	public static void debug(String message, Object... args) {
		LOGGER.debug(String.format(message, args));
		TTSMod.LOGGER_SLF4J.error(message, args);
	}

	public static void error(String message, Object... args) {
		LOGGER.error(String.format(message, args));
		TTSMod.LOGGER_SLF4J.error(message, args);
	}

	public static void info(String message, Object... args) {
		LOGGER.info(String.format(message, args));
		TTSMod.LOGGER_SLF4J.info(message, args);
	}

	public static void warn(String message, Object... args) {
		LOGGER.warn(String.format(message, args));
		TTSMod.LOGGER_SLF4J.warn(message, args);
	}
}
