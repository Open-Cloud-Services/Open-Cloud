package app.open.software.core.logger.component.impl;

import app.open.software.core.logger.*;
import app.open.software.core.logger.component.LoggerComponent;
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProgressBarComponent implements LoggerComponent {

	private final long length;

	private long current;

	private final LoggerContext context = Logger.getContext();

	public void print() {
		final var percent = this.getPercent();
		final var date = this.context.getDateFormat().format(new Date());

		final var builder = new StringBuilder("\r[");

		builder.append(date).append("] ");
		builder.append(this.context.getPrefix()).append(" [");
		builder.append(LogLevel.INFO.getName()).append("] ");
		builder.append(this.createProgress());

		if (percent == 100) {
			System.out.println(builder.toString());
			System.out.print("\r> ");

			this.onFinish();
		} else {
			System.out.print(builder.toString());
		}
	}

	public void logToFile() {

	}

	public void updateProgress(final long current) {
		this.current = current;
		this.print();
	}

	private int getPercent() {
		return (int) (((double) this.current / this.length) * 100);
	}

	private String createProgress() {
		var percent = this.getPercent() / 4;
		final var builder = new StringBuilder("[");

		for (var i = 0; i < 25; i++) {
			if (i < percent) {
				builder.append("=");
			} else if (i == percent) {
				builder.append(">");
			} else {
				builder.append(" ");
			}
		}

		builder.append("] ");
		builder.append(this.current / 1024).append("/");
		builder.append(this.length / 1024).append(" KB");

		return builder.toString();
	}

}
