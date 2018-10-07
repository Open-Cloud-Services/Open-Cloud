/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.file;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class LogFileHandler {

	private final Path latestLog = new File("logs//latest.log").toPath();

	public LogFileHandler() {
		if (Files.exists(this.latestLog)) {
			try {
				this.archiveLatestLog();

				Files.delete(this.latestLog);
			} catch (ZipException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void log(final String log) throws IOException {
		final var logsDir = new File("logs").toPath();
		this.checkDirectory(logsDir);

		this.checkFile(this.latestLog);

		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(this.latestLog, StandardOpenOption.APPEND)){
			bufferedWriter.write(log);
			bufferedWriter.newLine();
		}
	}

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

	private void checkDirectory(final Path directory) {
		if (Files.notExists(directory)) {
			try {
				Files.createDirectories(directory);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void checkFile(final Path file) {
		if (Files.notExists(file)) {
			try {
				Files.createFile(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void archiveLatestLog() throws ZipException, IOException {
		final var zipFile = new ZipFile(this.getNameForZip(this.testZipId()));
		zipFile.addFile(this.latestLog.toFile(), this.getZipParameters());
	}

	private String getNameForZip(final int count) throws IOException {
		final var time = Files.getLastModifiedTime(this.latestLog);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time.toMillis());

		final var builder = new StringBuilder("logs//");
		builder.append(calendar.get(Calendar.YEAR)).append("-");
		builder.append(calendar.get(Calendar.MONTH) + 1).append("-");
		builder.append(calendar.get(Calendar.DAY_OF_MONTH));
		builder.append("-").append(count);
		builder.append(".log.zip");

		return builder.toString();
	}

	private int testZipId() throws IOException {
		int id = 1;
		while (Files.exists(new File(this.getNameForZip(id)).toPath())) {
			id++;
		}
		return id;
	}

	private ZipParameters getZipParameters() {
		final var parameters = new ZipParameters();
		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

		return parameters;
	}

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
