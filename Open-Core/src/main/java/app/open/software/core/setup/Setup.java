/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.setup;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Interface which can be implemented for setups
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
public interface Setup {

	/**
	 * Setup the current module
	 *
	 * @param reader {@link BufferedReader} to read user input
	 *
	 * @throws IOException An I/O error occurred
	 */
	void setup(final BufferedReader reader) throws IOException;

}
