/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.component;

import app.open.software.core.logger.Logger;
import app.open.software.core.logger.LoggerContext;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Interface to implement several type of console and logging output
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public interface LoggerComponent {

	/**
	 * Print the component
	 */
	void print();

	/**
	 * The printing of the component is finished
	 */
	default void onFinish() {
		Logger.checkQueue();
	}

	/**
	 * @param context {@link LoggerContext} to get further information
	 *
	 * @return Created {@link StringBuilder}
	 */
	default StringBuilder createDefaultStringBuilder(final LoggerContext context) {
		final var builder = new StringBuilder("\r[");

		builder.append(this.getCurrentDate(context)).append("] ");
		builder.append(context.getPrefix()).append(" [");

		return builder;
	}

	/**
	 * @param context {@link LoggerContext} to get the {@link SimpleDateFormat}
	 *
	 * @return Current date as string
	 */
	default String getCurrentDate(final LoggerContext context) {
		return context.getDateFormat().format(new Date());
	}

}
