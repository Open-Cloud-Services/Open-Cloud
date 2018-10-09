/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core;

import app.open.software.core.logger.Logger;
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
		Logger.info("   ____                      _____ _                 _       ");
		Logger.info("  / __ \\                    / ____| |               | |     ");
		Logger.info(" | |  | |_ __   ___ _ __   | |    | | ___  _   _  __| |      ");
		Logger.info(" | |  | | '_ \\ / _ \\ '_ \\  | |    | |/ _ \\| | | |/ _` |  ");
		Logger.info(" | |__| | |_) |  __/ | | | | |____| | (_) | |_| | (_| |      ");
		Logger.info("  \\____/| .__/ \\___|_| |_|  \\_____|_|\\___/ \\__,_|\\__,_|");
		Logger.info("        | |                                                  ");
		Logger.info("        |_|                                                  ");

		this.delay(200);

		Logger.info("");

		Logger.info("Copyright (c) 2018 by Open-Software and contributors");
		Logger.info("Java version -> " + System.getProperty("java.version") + ", OS -> " + System.getProperty("os.name"));
		Logger.info("");

		this.delay(200);

		Logger.info("Starting " + module + "!");
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
