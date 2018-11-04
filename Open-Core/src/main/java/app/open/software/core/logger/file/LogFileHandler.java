/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.file;

import app.open.software.core.logger.Logger;
import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Handler to handle the logging into files
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class LogFileHandler {

	/**
	 * {@link Path} of the latest log
	 */
	private final Path latestLog = new File("logs/latest.log").toPath();

	public LogFileHandler() {
		if (Files.exists(this.latestLog)) {
			try {
				this.archiveLatestLog();

				Files.delete(this.latestLog);
			} catch (ZipException | IOException e) {
				Logger.error("Could not archive latest log", e);
			}
		}
	}

	/**
	 * Log content into the latest log file
	 *
	 * @param log Log content
	 *
	 * @throws IOException An I/O error occurs
	 */
	public void log(final String log) throws IOException {
		final var logsDir = new File("logs").toPath();
		this.checkDirectory(logsDir);

		this.checkFile(this.latestLog);

		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(this.latestLog, StandardOpenOption.APPEND)){
			bufferedWriter.write(log);
			bufferedWriter.newLine();
		}
	}

	/**
	 * Log an {@link ErrorRecord} to a single new {@link Path}
	 *
	 * @param error {@link ErrorRecord} to log
	 *
	 * @throws IOException An I/O error occurs
	 */
	public void logError(final ErrorRecord error) throws IOException {
		final Path errorLogDir = new File("errors").toPath();
		this.checkDirectory(errorLogDir);

		final var dateFormat = new SimpleDateFormat("HH-mm_dd-MM");
		final var errorFile = new File("errors/Error_" + dateFormat.format(new Date()) + ".json").toPath();

		this.checkFile(errorFile);

		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(errorFile)){
			bufferedWriter.write(this.serializeException(error));
		}
	}

	/**
	 * Create directory, if it not exists
	 *
	 * @param directory Directory to check
	 */
	private void checkDirectory(final Path directory) {
		if (Files.notExists(directory)) {
			try {
				Files.createDirectories(directory);
			} catch (IOException e) {
				Logger.error("Could not create directory", e);
			}
		}
	}

	public void deleteLogFiles() {
		Logger.info("Deleting log files...");
		Arrays.stream(new File("logs").listFiles()).forEach(file -> {
			try {
				Files.delete(file.toPath());
			} catch (IOException e) {
				Logger.error("Could not delete log file", e);
			}
		});
		Logger.info("Done!");
	}

	/**
	 * Create {@link Path}, if it not exists
	 *
	 * @param file {@link Path} to check
	 */
	private void checkFile(final Path file) {
		if (Files.notExists(file)) {
			try {
				Files.createFile(file);
			} catch (IOException e) {
				Logger.error("Could not create file", e);
			}
		}
	}

	/**
	 * Put the latest log {@link Path} into an zip archive
	 *
	 * @throws ZipException An I/O error occurs
	 * @throws IOException An I/O error occurs
	 */
	private void archiveLatestLog() throws ZipException, IOException {
		final var zipFile = new ZipFile(this.getNameForZip());
		zipFile.addFile(this.latestLog.toFile(), this.getZipParameters());
	}

	/**
	 * @return Name for the zip file
	 *
	 *  @throws IOException An I/O error occurs
	 */
	private String getNameForZip() throws IOException {
		final var millis = Files.getLastModifiedTime(this.latestLog);
		final var date = Instant.ofEpochMilli(millis.toMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime();

		final var builder = new StringBuilder("logs/");
		builder.append(DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm").format(date));
		builder.append(".log.zip");

		return builder.toString();
	}

	/**
	 * @return {@link ZipParameters} to create the zip archive
	 */
	private ZipParameters getZipParameters() {
		final var parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		return parameters;
	}

	/**
	 * @return Serialized {@link ErrorRecord} object
	 *
	 * @param error {@link ErrorRecord} to serialize
	 */
	private String serializeException(final ErrorRecord error) {
		return new GsonBuilder().setPrettyPrinting().addSerializationExclusionStrategy(new ExclusionStrategy() {

			public boolean shouldSkipField(final FieldAttributes fieldAttributes) {
				return fieldAttributes.getName().equals("classLoaderName") || fieldAttributes.getName().equals("format");
			}

			public boolean shouldSkipClass(final Class<?> aClass) {
				return false;
			}

		}).create().toJson(error);
	}

}
