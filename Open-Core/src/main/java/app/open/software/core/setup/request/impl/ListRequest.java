/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.setup.request.impl;

import app.open.software.core.logger.Logger;
import app.open.software.core.setup.request.Request;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Request an object from a list
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
public class ListRequest extends Request {

	public ListRequest(final String request, final BufferedReader reader) {
		super(request, reader);
	}

	/**
	 * Printing the request and parse the response
	 *
	 * @param responses List of possible responses
	 * @param callback {@link Consumer} to get a callback with the response
	 *
	 * @throws IOException If an I/O error occurred
	 */
	public <T> void request(final T[] responses, final Consumer<String> callback) throws IOException {
		Logger.info(this.request + " " + this.getResponseString(responses));
		final String input = this.reader.readLine();
		if (this.contains(responses, input)) {
			callback.accept(input);
		} else {
			this.request(responses, callback);
		}
	}

	/**
	 * @param responses Response array of all possible responses
	 * @param <T> Type of the response array
	 *
	 * @return String from all responses separated by commas
	 */
	private <T> String getResponseString(final T[] responses) {
		return String.join(", ", Arrays.toString(responses));
	}

	/**
	 * @param responses Array of the possible responses
	 * @param response Response which should be checked, if it is in the array
	 * @param <T> Type of the response array
	 *
	 * @return The array contains the specific {@param response}
	 */
	private <T> boolean contains(final T[] responses, final String response) {
		return Arrays.stream(responses).anyMatch(s -> s.toString().equalsIgnoreCase(response));
	}

}
