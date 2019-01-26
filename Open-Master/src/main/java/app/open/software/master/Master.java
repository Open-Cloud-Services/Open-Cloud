/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master;

import app.open.software.core.CloudApplication;
import app.open.software.core.bugsnag.BugsnagBootstrap;
import app.open.software.core.command.CommandService;
import app.open.software.core.config.DocumentFileProviderService;
import app.open.software.core.logger.*;
import app.open.software.core.service.ServiceCluster;
import app.open.software.core.updater.AutoUpdater;
import app.open.software.core.updater.UpdateType;
import app.open.software.event.service.EventService;
import app.open.software.master.config.ContainerConfig;
import app.open.software.master.config.MasterConfig;
import app.open.software.master.config.entity.ConfigEntity;
import app.open.software.master.container.ContainerEntityService;
import app.open.software.master.setup.MasterSetup;
import app.open.software.master.template.TemplateDeploymentHandler;
import app.open.software.rest.WebServer;
import app.open.software.rest.version.RestVersion;
import com.bugsnag.Bugsnag;
import java.io.*;
import java.util.HashMap;
import joptsimple.OptionSet;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.http.HttpStatus;
import static spark.Spark.halt;

/**
 * Open-Master main class to control everything
 *
 * @author Tammo0987
 * @version 1.2
 * @since 0.1
 */
public class Master implements CloudApplication {

	/**
	 * Singleton instance of {@link Master}
	 */
	@Getter
	private static Master master;

	/**
	 * Instance of {@link ConfigEntity}
	 */
	@Setter
	@Getter
	private ConfigEntity configEntity = new ConfigEntity();

	/**
	 * Instance of {@link WebServer}
	 */
	private WebServer webServer;

	/**
	 * {@inheritDoc}
	 */
	public void start(final OptionSet optionSet, final long time) {
		if (master == null) master = this;

		final BugsnagBootstrap bugsnagBootstrap = new BugsnagBootstrap("Open-Master", this.getVersion());
		final Bugsnag bugsnag = bugsnagBootstrap.getBugsnag();

		Logger.setContext(new LoggerContext("Open-Master", optionSet.has("debug") ? LogLevel.DEBUG : LogLevel.INFO, bugsnag));

		if (!optionSet.has("disable-updater") && new AutoUpdater(this.getVersion(), UpdateType.MASTER).checkForUpdate()) {
			return;
		}

		if (this.handleParameters(optionSet)) {
			return;
		}

		this.printStartHeader("Open-Master");

		ServiceCluster.addServices(
				new CommandService(),
				new ContainerEntityService(),
				new EventService(),
				new DocumentFileProviderService()
		);

		ServiceCluster.get(DocumentFileProviderService.class).addFiles(
				new ContainerConfig(),
				new MasterConfig()
		);

		ServiceCluster.init();

		if (optionSet.has("time")) {
			Logger.info("Time to start: " + (System.currentTimeMillis() - time) + " ms");
		}

		try {
			new MasterSetup().setup(new BufferedReader(new InputStreamReader(System.in)));
		} catch (IOException e) {
			Logger.error("Reading of input failed!", e);
		}

		this.webServer = new WebServer(8080);
		this.webServer.registerVersions(new RestVersion(1).registerHandlers(new TemplateDeploymentHandler()));

		this.webServer.start((request, response) -> {
			if (request.headers("X-Auth-Token") == null || ServiceCluster.get(ContainerEntityService.class).getContainerMetas().stream().noneMatch(containerMeta -> containerMeta.getToken().equals(request.headers("X-Auth-Token")))) {
				halt(HttpStatus.UNAUTHORIZED_401);
			}
		});

		ServiceCluster.get(CommandService.class).start();
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {
		Logger.info("Starting shutdown sequence!");

		ServiceCluster.stop();

		this.webServer.stop();

		Logger.info("Stopped Open-Master");

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
