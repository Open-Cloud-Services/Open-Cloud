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
import java.util.Date;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorComponent implements LoggerComponent {

	private final String error;

	private final Exception exception;

	private final LoggerContext context = Logger.getContext();

	public void print() {
		final var date = this.context.getDateFormat().format(new Date());

		final var builder = new StringBuilder("\r[");

		builder.append(date).append("] ");
		builder.append(this.context.getPrefix()).append(" [");
		builder.append(LogLevel.ERROR.getName()).append("] ");

		builder.append(this.error);

		System.out.println(builder.toString());

		this.exception.printStackTrace();

		System.out.print("\r> ");

		try {
			Logger.getFileHandler().logError(new ErrorRecord("[" + date + "] " + this.error, this.exception));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.onFinish();
	}

}
