/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container;

import app.open.software.container.config.ContainerConfig;
import app.open.software.container.config.entity.ConfigEntity;
import app.open.software.container.setup.ContainerSetup;
import app.open.software.core.CloudApplication;
import app.open.software.core.bugsnag.BugsnagBootstrap;
import app.open.software.core.command.CommandService;
import app.open.software.core.config.DocumentFileProviderService;
import app.open.software.core.logger.*;
import app.open.software.core.service.ServiceCluster;
import app.open.software.core.updater.AutoUpdater;
import app.open.software.core.updater.UpdateType;
import app.open.software.event.service.EventService;
import com.bugsnag.Bugsnag;
import java.io.*;
import java.util.HashMap;
import joptsimple.OptionSet;
import lombok.Getter;
import lombok.Setter;

/**
 * Open-Container main class to control everything
 *
 * @author Tammo0987
 * @version 1.1
 * @since 0.1
 */
public class Container implements CloudApplication {

	/**
	 * Singleton instance of {@link Container}
	 */
	@Getter
	private static Container container;

	/**
	 * Instance of {@link ConfigEntity} as config object
	 */
	@Setter
	@Getter
	private ConfigEntity configEntity = new ConfigEntity();

	/**
	 * {@inheritDoc}
	 */
	public void start(final OptionSet optionSet, final long time) {
		if(container == null) container = this;

		final BugsnagBootstrap bugsnagBootstrap = new BugsnagBootstrap("Open-Container", this.getVersion());
		final Bugsnag bugsnag = bugsnagBootstrap.getBugsnag();

		Logger.setContext(new LoggerContext("Open-Container", optionSet.has("debug") ? LogLevel.DEBUG : LogLevel.INFO, bugsnag));

		if (!optionSet.has("disable-updater") && new AutoUpdater(this.getVersion(), UpdateType.CONTAINER).checkForUpdate()) {
			return;
		}

		if (this.handleParameters(optionSet)) {
			return;
		}

		this.printStartHeader("Open-Container");

		ServiceCluster.addServices(
				new CommandService(),
				new EventService()
				);

		ServiceCluster.addServices(new DocumentFileProviderService().addFiles(
				new ContainerConfig()
		));

		ServiceCluster.init();

		if (optionSet.has("time")) {
			Logger.info("Time to start: " + (System.currentTimeMillis() - time) + " ms");
		}

		try {
			new ContainerSetup().setup(new BufferedReader(new InputStreamReader(System.in)));
		} catch (IOException e) {
			Logger.error("Could not read input from the console!", e);
		}

		//TODO setup server + send verify
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {
		Logger.info("Starting shutdown sequence!");

		ServiceCluster.stop();

		//TODO send disconnected

		Logger.info("Stopped Open-Container");

		System.exit(0);
	}

	/**
	 * Add specific module parameter help
	 *
	 * @param hashMap Map of all parameters with description
	 */
	public void addParameterHelp(final HashMap hashMap) {

	}

}
