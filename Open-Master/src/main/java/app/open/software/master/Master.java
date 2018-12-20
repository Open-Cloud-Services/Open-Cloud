/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master;

import app.open.software.core.CloudApplication;
import app.open.software.core.command.CommandService;
import app.open.software.core.config.DocumentFileProviderService;
import app.open.software.core.logger.*;
import app.open.software.core.service.ServiceCluster;
import app.open.software.master.bootstrap.BugsnagBootstrap;
import com.bugsnag.Bugsnag;
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
	public void start(final OptionSet set, final long time) {
		if(master == null) master = this;

		final BugsnagBootstrap bugsnagBootstrap = new BugsnagBootstrap();
		final Bugsnag bugsnag = bugsnagBootstrap.getBugsnag();

		Logger.setContext(new LoggerContext("Open-Master", set.has("debug") ? LogLevel.DEBUG : LogLevel.INFO, bugsnag));

		if (set.has("help")) {
			this.printArgumentHelp();
			return;
		}

		this.printStartHeader("Open-Master");

		ServiceCluster.addServices(
				new CommandService(),
				new DocumentFileProviderService()
		);

		ServiceCluster.init();

		if (set.has("time")) {
			Logger.info("Time to start: " + (System.currentTimeMillis() - time) + " ms");
		}

		ServiceCluster.get(CommandService.class).start();
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {
		Logger.info("Starting shutdown sequence!");

		ServiceCluster.stop();

		Logger.info("Stopped Open-Master");

		System.exit(0);
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
