/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.setup;

import java.io.BufferedReader;
import lombok.RequiredArgsConstructor;

/**
 * Request class for different types of requests for the setup
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
@RequiredArgsConstructor
public abstract class Request {

	/**
	 * Requested question
	 */
	protected final String request;

	/**
	 * {@link BufferedReader} to read the user input
	 */
	protected final BufferedReader reader;

}
