package app.open.software.core.logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

class LogFileHandler {

	private final File rootDirectory;

	private final SimpleDateFormat monthFormat = new SimpleDateFormat("MM");

	private final SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

	LogFileHandler(final File rootDirectory) {
		this.rootDirectory = rootDirectory;

		if (Files.notExists(this.rootDirectory.toPath())) {
			try {
				Files.createDirectories(this.rootDirectory.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	void log(final String log) throws IOException {
		final var month = monthFormat.format(new Date());
		final var monthDir = new File(this.rootDirectory.getPath() + "//" + month).toPath();

		if (Files.notExists(monthDir)) {
			Files.createDirectory(monthDir);
		}

		final var day = dayFormat.format(new Date());
		final var logFile = new File(monthDir.toString() + "//" + day + ".log");

		if (Files.notExists(logFile.toPath())) {
			Files.createFile(logFile.toPath());
		}

		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(logFile.toPath(), StandardOpenOption.APPEND)) {
			bufferedWriter.write(log);
			bufferedWriter.newLine();
		}
	}

	void logError(final String error, final Exception exception) throws IOException {
		final var errorFile = new File("").toPath();
		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(errorFile)){
			bufferedWriter.write(error);
			bufferedWriter.newLine();
			bufferedWriter.write(exception.toString());
		}
	}

}
