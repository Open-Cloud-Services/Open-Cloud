/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.file;

public class ErrorRecord {

	private final String error;

	private final String exception;

	private final Exception stackTrace;

	public ErrorRecord(final String error, final Exception stackTrace) {
		this.error = error;
		this.stackTrace = stackTrace;
		this.exception = stackTrace.getClass().getSimpleName();
	}
}
