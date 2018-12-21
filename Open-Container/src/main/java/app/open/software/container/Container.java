/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container;

import app.open.software.core.CloudApplication;
import app.open.software.core.bugsnag.BugsnagBootstrap;
import app.open.software.core.command.CommandService;
import app.open.software.core.logger.*;
import app.open.software.core.service.ServiceCluster;
import com.bugsnag.Bugsnag;
import java.util.HashMap;
import joptsimple.OptionSet;
import lombok.Getter;

/**
 * Open-Container main class to control everything
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class Container implements CloudApplication {

	/**
	 * Singleton instance of {@link Container}
	 */
	@Getter
	private static Container container;

	/**
	 * {@inheritDoc}
	 */
	public void start(final OptionSet set, final long time) {
		if(container == null) container = this;

		final BugsnagBootstrap bugsnagBootstrap = new BugsnagBootstrap("Open-Container", this.getVersion());
		final Bugsnag bugsnag = bugsnagBootstrap.getBugsnag();

		Logger.setContext(new LoggerContext("Open-Container", set.has("debug") ? LogLevel.DEBUG : LogLevel.INFO, bugsnag));

		if (set.has("help")) {
			this.printArgumentHelp();
			return;
		}

		this.printStartHeader("Open-Container");

		if (set.has("time")) {
			Logger.info("Time to start: " + (System.currentTimeMillis() - time) + " ms");
		}

		ServiceCluster.addServices(new CommandService());
		ServiceCluster.init();

		ServiceCluster.get(CommandService.class).start();
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {
		Logger.info("Starting shutdown sequence!");

		ServiceCluster.get(CommandService.class).stop();

		Logger.info("Stopped Open-Container");
	}

	/**
	 * Print the help for the program arguments if requested
	 */
	private void printArgumentHelp() {
		final var map = new HashMap<String, String>();
		map.put("help", "Print all possible runtime arguments");
		map.put("version", "Print the current version of Open-Cloud");
		map.put("debug", "Enable debug logging");
		map.put("time", "Show after starting the time to start");

		Logger.info("Open-Cloud Help:");
		Logger.info("");
		map.forEach((name, description) -> Logger.info(name + " -> " + description));
		Logger.info("");
	}

}
