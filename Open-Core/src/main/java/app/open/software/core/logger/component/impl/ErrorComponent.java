/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.component.impl;

import app.open.software.core.logger.*;
import app.open.software.core.logger.component.LoggerComponent;
import app.open.software.core.logger.file.ErrorRecord;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of {@link LoggerComponent} for errors
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
@RequiredArgsConstructor
public class ErrorComponent implements LoggerComponent {

	/**
	 * Specific error
	 */
	private final String error;

	/**
	 * {@link Exception} from the error
	 */
	private final Exception exception;

	/**
	 * {@link LoggerContext} from the {@link Logger} for formatting
	 */
	private final LoggerContext context = Logger.getContext();

	/**
	 * {@inheritDoc}
	 */
	public void print() {
		final var builder = this.createDefaultStringBuilder(this.context);

		builder.append(LogLevel.ERROR.getName()).append("] ");

		builder.append(this.error);

		System.out.println(builder.toString());

		this.exception.printStackTrace();

		System.out.print("\r> ");

		try {
			Logger.getFileHandler().logError(new ErrorRecord("[" + this.getCurrentDate() + "] " + this.error, this.exception));
		} catch (IOException e) {
			Logger.error("Could not log error", e);
		}

		this.onFinish();
	}

}
