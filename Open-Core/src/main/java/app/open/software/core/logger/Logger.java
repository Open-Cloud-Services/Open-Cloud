/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger;

import app.open.software.core.logger.component.LoggerComponent;
import app.open.software.core.logger.component.impl.*;
import app.open.software.core.logger.file.LogFileHandler;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Getter;
import lombok.Setter;

/**
 * Log content to the console and files, to see errors and useful information about the runtime
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class Logger {

	/**
	 * {@link LoggerContext} for further information about the prefix and the current time
	 */
	@Setter
	@Getter
	private static LoggerContext context;

	/**
	 * {@link LogFileHandler} to handle the log files
	 */
	@Getter
	private static final LogFileHandler fileHandler = new LogFileHandler();

	/**
	 * {@link Queue} to have an event driven architecture
	 */
	private static final Queue<LoggerComponent> queue = new LinkedBlockingQueue<>();

	/**
	 * Log content with the {@link LogLevel#DEBUG}
	 *
	 * @param log Content of the log
	 */
	public static void debug(final String log) {
		log(log, LogLevel.DEBUG);
	}

	/**
	 * Log content with the {@link LogLevel#INFO}
	 *
	 * @param log Content of the log
	 */
	public static void info(final String log) {
		log(log, LogLevel.INFO);
	}

	/**
	 * Warn with the {@link LogLevel#WARNING}
	 *
	 * @param warning Specific warning
	 */
	public static void warn(final String warning) {
		log(warning, LogLevel.WARNING);
	}

	/**
	 * Log error with the {@link LogLevel#ERROR}
	 *
	 * @param error Specific error message
	 * @param exception Specific {@link Exception}
	 */
	public static void error(final String error, final Exception exception) {
		context.getBugsnag().notify(exception);
		queue.offer(new ErrorComponent(error, exception));
		checkQueue();
	}

	/**
	 * Log content with the {@link LogLevel} of the level parameter
	 *
	 * @param log Content to log
	 * @param level {@link LogLevel} for the content
	 */
	private static void log(final String log, final LogLevel level) {
		queue.offer(new TextComponent(log, level));
		checkQueue();
	}

	/**
	 * Create and start a {@link ProgressBarComponent}
	 *
	 * @param component Instance of {@link ProgressBarComponent}
	 */
	public static void progress(final ProgressBarComponent component) {
		queue.offer(component);
		checkQueue();
	}

	/**
	 * Check if the {@link Logger#queue} has more elements to log. If it has, then log the {@link LoggerComponent}
	 */
	public static void checkQueue() {
		if (queue.isEmpty()) return;

		final var component = queue.poll();
		component.print();
	}

}
