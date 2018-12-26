/*
 * Copyright (c) 2018, Open-Software and contributors
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
import app.open.software.master.config.ContainerConfig;
import app.open.software.master.config.MasterConfig;
import app.open.software.master.config.entity.ConfigEntity;
import app.open.software.master.container.ContainerEntityService;
import app.open.software.master.network.PacketHandler;
import app.open.software.master.setup.MasterSetup;
import app.open.software.protocol.ProtocolServer;
import app.open.software.protocol.handler.PacketDecoder;
import app.open.software.protocol.handler.PacketEncoder;
import com.bugsnag.Bugsnag;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import java.io.*;
import java.util.HashMap;
import joptsimple.OptionSet;
import lombok.Getter;
import lombok.Setter;

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
	 * Instance of {@link ProtocolServer}
	 */
	private ProtocolServer protocolServer;

	/**
	 * Instance of {@link ConfigEntity}
	 */
	@Setter
	@Getter
	private ConfigEntity configEntity = new ConfigEntity();

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

		this.setupServer(this.configEntity.getPort());

		ServiceCluster.get(CommandService.class).start();
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {
		Logger.info("Starting shutdown sequence!");

		ServiceCluster.stop();

		try {
			this.protocolServer.shutdown(() -> Logger.info("Closed server!"));
		} catch (InterruptedException e) {
			Logger.error("Server interrupted while closing", e);
		}

		Logger.info("Stopped Open-Master");
	}

	private void setupServer(final int port) {
		this.registerPackets();

		this.protocolServer = new ProtocolServer(port).bind(() -> Logger.info("Successfully bound server to port " + port), new ChannelInitializer<>() {

			protected void initChannel(final Channel channel) {
				channel.pipeline().addLast(new PacketEncoder(), new PacketDecoder(), new PacketHandler());
				Logger.info("Container connected from " + ServiceCluster.get(ContainerEntityService.class).getHostByChannel(channel));
			}

		});

	}

	private void registerPackets() {

	}

	/**
	 * Add specific module parameter help
	 *
	 * @param hashMap Map of all parameters with description
	 */
	public void addParameterHelp(final HashMap hashMap) {

	}

}
