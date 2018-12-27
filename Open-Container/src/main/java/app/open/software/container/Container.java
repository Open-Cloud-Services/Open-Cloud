/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container;

import app.open.software.container.config.ContainerConfig;
import app.open.software.container.config.entity.ConfigEntity;
import app.open.software.container.network.PacketHandler;
import app.open.software.container.network.packets.ContainerKeyValidationOutPacket;
import app.open.software.container.network.packets.ContainerKeyValidationResponseInPacket;
import app.open.software.container.setup.ContainerSetup;
import app.open.software.core.CloudApplication;
import app.open.software.core.bugsnag.BugsnagBootstrap;
import app.open.software.core.command.CommandService;
import app.open.software.core.config.DocumentFileProviderService;
import app.open.software.core.logger.*;
import app.open.software.core.service.ServiceCluster;
import app.open.software.core.updater.AutoUpdater;
import app.open.software.core.updater.UpdateType;
import app.open.software.protocol.NetworkConnectionEntity;
import app.open.software.protocol.ProtocolClient;
import app.open.software.protocol.handler.PacketDecoder;
import app.open.software.protocol.handler.PacketEncoder;
import app.open.software.protocol.packet.Packet;
import app.open.software.protocol.packet.impl.ErrorPacket;
import app.open.software.protocol.packet.impl.SuccessPacket;
import app.open.software.protocol.packet.registry.PacketRegistry;
import com.bugsnag.Bugsnag;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import java.io.*;
import java.util.HashMap;
import joptsimple.OptionSet;
import lombok.Getter;
import lombok.Setter;

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
	 * Instance of {@link ProtocolClient} to connect to the Open-Master
	 */
	@Getter
	private ProtocolClient protocolClient;

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

		ServiceCluster.addServices(new CommandService());

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

		this.setupClient();
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {
		Logger.info("Starting shutdown sequence!");

		ServiceCluster.stop();

		if (this.protocolClient.isConnected()) {
			this.protocolClient.disconnect(() -> Logger.info("Client disconnected!"));
		}

		Logger.info("Stopped Open-Container");

		System.exit(0);
	}

	/**
	 * Setup client and connect to the Open-Master
	 */
	private void setupClient() {
		this.registerPackets();

		this.protocolClient = new ProtocolClient(new NetworkConnectionEntity(this.configEntity.getHost(), this.configEntity.getPort()))
				.connect(() -> {
					ServiceCluster.get(CommandService.class).start();
					this.protocolClient.sendPacket(new ContainerKeyValidationOutPacket(this.configEntity.getKey()));
				}, () -> {
					Logger.warn("Could not connect to Open-Master!");
					this.shutdown();
				}, new ChannelInitializer<>() {

					protected void initChannel(final Channel channel) {
						channel.pipeline().addLast(new PacketEncoder(), new PacketDecoder(), new PacketHandler());
					}

				});
	}

	/**
	 * Register {@link Packet}s to identify by id
	 */
	private void registerPackets() {
		PacketRegistry.IN.addPacket(0, SuccessPacket.class);
		PacketRegistry.IN.addPacket(1, ErrorPacket.class);

		PacketRegistry.IN.addPacket(401, ContainerKeyValidationResponseInPacket.class);

		PacketRegistry.OUT.addPacket(0, SuccessPacket.class);
		PacketRegistry.OUT.addPacket(1, ErrorPacket.class);

		PacketRegistry.OUT.addPacket(400, ContainerKeyValidationOutPacket.class);
	}

	/**
	 * Add specific module parameter help
	 *
	 * @param hashMap Map of all parameters with description
	 */
	public void addParameterHelp(final HashMap hashMap) {

	}

}
