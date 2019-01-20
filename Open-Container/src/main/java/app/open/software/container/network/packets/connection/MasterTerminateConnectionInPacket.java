/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.network.packets.connection;

import app.open.software.container.Container;
import app.open.software.core.logger.Logger;
import app.open.software.protocol.packet.Packet;
import io.netty.channel.Channel;

/**
 * Master terminated the connection
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public class MasterTerminateConnectionInPacket implements Packet {

	/**
	 * {@inheritDoc}
	 */
	public final Packet process(final Channel channel) {
		Logger.info("Open-Master has terminated the connection!");
		Container.getContainer().shutdown();
		return null;
	}

}
