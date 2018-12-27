/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.network.packets;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.ServiceCluster;
import app.open.software.master.container.ContainerEntity;
import app.open.software.master.container.ContainerEntityService;
import app.open.software.protocol.packet.Packet;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import java.io.IOException;

/**
 * Receive key from a {@link ContainerEntity} to authenticate
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public class ContainerKeyValidationInPacket implements Packet {

	/**
	 * Key for authentication
	 */
	private String key;

	/**
	 * {@inheritDoc}
	 */
	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.key = byteBuf.readUTF();
	}

	/**
	 * {@inheritDoc}
	 */
	public Packet process(final Channel channel) {
		final ContainerEntity containerEntity = ServiceCluster.get(ContainerEntityService.class).getContainerByChannel(channel);
		final boolean verified = containerEntity.getContainerMeta().getKey().equals(this.key);
		containerEntity.setVerified(verified);

		if (verified) {
			containerEntity.setChannel(channel);
			containerEntity.getPacketQueue().forEach(containerEntity::sendPacket);
			containerEntity.getPacketQueue().clear();

			Logger.info("Container connected from " + ServiceCluster.get(ContainerEntityService.class).getHostByChannel(channel));
		} else {
			Logger.info("Container sent wrong key!");
		}

		return new ContainerKeyValidationResponseOutPacket(verified);
	}

}
