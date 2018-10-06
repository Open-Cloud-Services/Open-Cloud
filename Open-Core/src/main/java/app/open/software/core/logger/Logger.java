package app.open.software.core.logger;

import app.open.software.core.logger.component.LoggerComponent;
import app.open.software.core.logger.component.impl.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Getter;
import lombok.Setter;

public class Logger {

	@Setter
	@Getter
	private static LoggerContext context;

	private static Queue<LoggerComponent> queue = new LinkedBlockingQueue<>();

	public static void debug(final String log) {
		log(log, LogLevel.DEBUG);
	}

	public static void info(final String log) {
		log(log, LogLevel.INFO);
	}

	public static void warn(final String warning) {
		log(warning, LogLevel.WARNING);
	}

	public static void error(final String error, final Exception exception) {
		queue.offer(new ErrorComponent(error, exception));
		checkQueue();
	}

	private static void log(final String log, final LogLevel level) {
		queue.offer(new TextComponent(log, level));
		checkQueue();
	}

	public static ProgressBarComponent progress(final long length) {
		final var component = new ProgressBarComponent(length);
		queue.offer(component);
		checkQueue();
		return component;
	}

	public static void checkQueue() {
		if (queue.isEmpty()) return;

		final var component = queue.poll();
		component.print();
		component.logToFile();
	}

}
