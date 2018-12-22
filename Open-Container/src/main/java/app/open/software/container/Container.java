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
	public void start(final OptionSet optionSet, final long time) {
		if(container == null) container = this;

		final BugsnagBootstrap bugsnagBootstrap = new BugsnagBootstrap("Open-Container", this.getVersion());
		final Bugsnag bugsnag = bugsnagBootstrap.getBugsnag();

		Logger.setContext(new LoggerContext("Open-Container", optionSet.has("debug") ? LogLevel.DEBUG : LogLevel.INFO, bugsnag));

		if (this.handleParameters(optionSet)) {
			return;
		}

		this.printStartHeader("Open-Container");

		ServiceCluster.addServices(new CommandService());
		ServiceCluster.init();

		if (optionSet.has("time")) {
			Logger.info("Time to start: " + (System.currentTimeMillis() - time) + " ms");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {
		Logger.info("Starting shutdown sequence!");

		ServiceCluster.stop();

		Logger.info("Stopped Open-Container");
	}

	/**
	 * Add specific module parameter help
	 *
	 * @param hashMap Map of all parameters with description
	 */
	public void addParameterHelp(final HashMap hashMap) {

	}

}
