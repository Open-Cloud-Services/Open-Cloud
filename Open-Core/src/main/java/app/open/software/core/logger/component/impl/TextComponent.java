/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.component.impl;

import app.open.software.core.logger.*;
import app.open.software.core.logger.component.LoggerComponent;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of the {@link LoggerComponent} for normal log
 */
@RequiredArgsConstructor
public class TextComponent implements LoggerComponent {

	/**
	 * Content to log
	 */
	private final String log;

	/**
	 * {@link LogLevel} of the log content
	 */
	private final LogLevel level;

	/**
	 * {@link LoggerContext} from the {@link Logger} for formatting
	 */
	private final LoggerContext context = Logger.getContext();

	/**
	 * {@inheritDoc}
	 */
	public void print() {
		if (this.level.getLevel() < this.context.getLevel().getLevel()) {
			this.onFinish();
			return;
		}

		final var builder = this.createDefaultStringBuilder(this.context);

		builder.append(this.level.getName()).append("] ");

		builder.append(this.log);

		System.out.println(builder.toString());

		System.out.print("\r> ");

		try {
			Logger.getFileHandler().log(builder.toString().replace("\r", ""));
		} catch (IOException e) {
			Logger.error("Could not file log log", e);
		}

		this.onFinish();
	}

}
