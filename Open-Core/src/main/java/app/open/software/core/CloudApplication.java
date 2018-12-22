/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core;

import app.open.software.core.logger.Logger;
import java.util.HashMap;
import joptsimple.OptionSet;

/**
 * Interface to implement to the main class of the application
 *
 * @author Tammo0987
 * @version 1.1
 * @since 0.1
 */
public interface CloudApplication {

	/**
	 * Starts the {@link CloudApplication}
	 *
	 * @param optionSet {@link OptionSet} of the console arguments
	 * @param time Time in ms, when the application was started
	 */
	void start(final OptionSet optionSet, final long time);

	/**
	 * Stops the {@link CloudApplication}
	 */
	void shutdown();

	/**
	 * Handle the program parameters
	 *
	 * @param optionSet {@link OptionSet} of current parameters
	 */
	 default boolean handleParameters(final OptionSet optionSet) {
		 if (optionSet.has("version")) {
			 Logger.info("Current version: " + this.getVersion());
			 return true;
		 }

		 if (optionSet.has("help")) {
			 this.printParametersHelp();
			 return true;
		 }

		 return false;
	 }

	/**
	 * Print the help for the program arguments if requested
	 */
	default void printParametersHelp() {
		final var map = new HashMap<String, String>();

		map.put("help", "Print all possible runtime arguments");
		map.put("version", "Print the current version of Open-Cloud");
		map.put("debug", "Enable debug logging");
		map.put("time", "Show after starting the time to start");

		this.addParameterHelp(map);

		Logger.info("<-- Open-Cloud Help -->");
		Logger.info("");
		map.forEach((name, description) -> Logger.info(name + " -> " + description));
		Logger.info("");
	}

	/**
	 * Add specific module parameter help
	 *
	 * @param hashMap Map of all parameters with description
	 */
	void addParameterHelp(final HashMap hashMap);

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
			Logger.error("Could not sleep as thread", e);
		}
	}

	/**
	 * @return Version of the system
	 */
	default String getVersion() {
		final String version = this.getClass().getPackage().getImplementationVersion();
		return version == null ? "Dev-Version" : version;
	}

}
