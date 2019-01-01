/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.component.impl;

import app.open.software.core.logger.*;
import app.open.software.core.logger.component.LoggerComponent;
import lombok.RequiredArgsConstructor;

/**
 * Implementation from the {@link LoggerComponent} to print a progress bar
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
@RequiredArgsConstructor
public class ProgressBarComponent implements LoggerComponent {

	/**
	 * Length of the downloaded file in bytes
	 */
	private final long length;

	/**
	 * Current downloaded bytes
	 */
	private long current;

	/**
	 * {@link LoggerContext} from the {@link Logger} for formatting
	 */
	private final LoggerContext context = Logger.getContext();

	/**
	 * {@inheritDoc}
	 */
	public void print() {
		final var percent = this.getPercent();

		final var builder = this.createDefaultStringBuilder(this.context);

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

	/**
	 * Update the size of current downloaded bytes
	 *
	 * @param current Current downloaded bytes
	 */
	public void updateProgress(final long current) {
		this.current = current;
		this.print();
	}

	/**
	 * @return The progress as percent
	 */
	private int getPercent() {
		return (int) (((double) this.current / this.length) * 100);
	}

	/**
	 * @return Progress bar as a {@link String}
	 */
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
