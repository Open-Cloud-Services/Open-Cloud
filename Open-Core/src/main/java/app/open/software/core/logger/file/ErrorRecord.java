/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.file;

import lombok.Value;

/**
 * {@link ErrorRecord} object to serialize it into json
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
@Value
public class ErrorRecord {

	/**
	 * Specific error message
	 */
	private final String error;

	/**
	 * Name of the {@link Exception}
	 */
	private final String exception;

	/**
	 * {@link Exception} of zhe error
	 */
	private final Exception stackTrace;

	public ErrorRecord(final String error, final Exception stackTrace) {
		this.error = error;
		this.stackTrace = stackTrace;
		this.exception = stackTrace.getClass().getSimpleName();
	}
}
