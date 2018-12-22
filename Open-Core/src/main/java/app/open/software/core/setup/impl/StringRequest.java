/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.setup.impl;

import app.open.software.core.logger.Logger;
import app.open.software.core.setup.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Request a string input
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
public class StringRequest extends Request {

	public StringRequest(final String request, final BufferedReader reader) {
		super(request, reader);
	}

	/**
	 * Request and process the input
	 *
	 * @param callback {@link Consumer} to process the user input
	 *
	 * @throws IOException An I/O error occurred
	 */
	public void request(final Consumer<String> callback) throws IOException {
		Logger.info(this.request);
		final String input = this.reader.readLine();

		if (input.trim().isEmpty()) {
			this.request(callback);
		} else {
			callback.accept(input);
		}
	}

}
