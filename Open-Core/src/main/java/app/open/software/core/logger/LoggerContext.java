/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger;

import com.bugsnag.Bugsnag;
import lombok.Value;

/**
 * Inject into the {@link Logger} for information about the actual {@link Logger} context
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
@Value
public class LoggerContext {

	/**
	 * Prefix for the {@link Logger}
	 */
	private final String prefix;

	/**
	 * Highest level of showing output
	 */
	private final LogLevel level;

	/**
	 * Instance of {@link Bugsnag} to report errors
	 */
	private final Bugsnag bugsnag;

}
