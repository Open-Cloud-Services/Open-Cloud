/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master;

import app.open.software.core.CloudApplication;
import app.open.software.core.logger.*;
import java.util.HashMap;
import joptsimple.OptionSet;
import lombok.Getter;

/**
 * Open-Master main class to control everything
 *
 * @author Tammo0987
 * @version 1.1
 * @since 0.1
 */
public class Master implements CloudApplication {

	/**
	 * Singleton instance of {@link Master}
	 */
	@Getter
	private static Master master;

	/**
	 * {@inheritDoc}
	 */
	public void start(final OptionSet set) {
		if(master == null) master = this;

		Logger.setContext(new LoggerContext("Open-Master", set.has("debug") ? LogLevel.DEBUG : LogLevel.INFO));

		if (set.has("help")) {
			this.printArgumentHelp();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {

	}

	/**
	 * Print the help for the program arguments if requested
	 */
	private void printArgumentHelp() {
		final var map = new HashMap<String, String>();
		map.put("help", "Print all possible runtime arguments");
		map.put("version", "Print the current version of Open-Cloud");
		map.put("debug", "Enable debug logging");

		Logger.info("Open-Cloud Help:");
		Logger.info("");
		map.forEach((name, description) -> Logger.info(name + " -> " + description));
		Logger.info("");
	}
}
