package app.open.software.core.logger.component.impl;

import app.open.software.core.logger.*;
import app.open.software.core.logger.component.LoggerComponent;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TextComponent implements LoggerComponent {

	private final String log;

	private final LogLevel level;

	private final LoggerContext context = Logger.getContext();

	public void print() {
		if (this.level.getLevel() < this.context.getLevel().getLevel()) {
			this.onFinish();
			return;
		}

		final var date = this.context.getDateFormat().format(new Date());

		final var builder = new StringBuilder("\r[");

		builder.append(date).append("] ");
		builder.append(this.context.getPrefix()).append(" [");
		builder.append(this.level.getName()).append("] ");

		builder.append(this.log);

		System.out.println(builder.toString());

		System.out.print("\r> ");

		this.onFinish();
	}

	public void logToFile() {

	}
}
