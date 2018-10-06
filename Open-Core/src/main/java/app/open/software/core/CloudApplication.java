/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core;

import joptsimple.OptionSet;

/**
 * Interface to implement to the main class of the application
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public interface CloudApplication {

	/**
	 * Starts the {@link CloudApplication}
	 *
	 * @param set {@link OptionSet} of the console arguments
	 */
	void start(final OptionSet set);

	/**
	 * Stops the {@link CloudApplication}
	 */
	void shutdown();

	/**
	 * Prints the header at startup to show information about the running application
	 *
	 * @param module Name from the started {@link CloudApplication}
	 */
	default void printStartHeader(final String module) {
		//TODO Implement Logger first to print the Header
	}

	/**
	 * Delay the running {@link CloudApplication} for a nicer startup
	 *
	 * @param time The length of the delay
	 */
	default void delay(final long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
