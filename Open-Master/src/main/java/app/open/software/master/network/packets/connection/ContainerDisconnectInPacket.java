/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.network.packets.connection;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.ServiceCluster;
import app.open.software.master.container.ContainerEntity;
import app.open.software.master.container.ContainerEntityService;
import app.open.software.protocol.packet.Packet;
import io.netty.channel.Channel;

/**
 * Get info from a container, that he stopped
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public class ContainerDisconnectInPacket implements Packet {

	/**
	 * {@inheritDoc}
	 */
	public final Packet process(final Channel channel) {
		final ContainerEntity containerEntity = ServiceCluster.get(ContainerEntityService.class).getContainerByChannel(channel);

		if (containerEntity != null) {
			containerEntity.setChannel(null);
			Logger.info("Container from " + containerEntity.getContainerMeta().getHost() + " disconnected!");
		}

		return null;
	}

}
