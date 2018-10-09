/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A level to differentiate the log content
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
@RequiredArgsConstructor
public enum LogLevel {

	DEBUG(0),
	INFO(1),
	WARNING(2),
	ERROR(3);

	/**
	 * Int value of the {@link LogLevel} to comparing
	 */
	@Getter
	private final int level;

	/**
	 * @return The name of the {@link LogLevel} in the right case
	 */
	public final String getName() {
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}
}
