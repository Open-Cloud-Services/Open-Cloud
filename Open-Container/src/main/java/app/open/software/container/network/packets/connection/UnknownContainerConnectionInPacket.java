/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.network.packets.connection;

import app.open.software.container.Container;
import app.open.software.core.logger.Logger;
import app.open.software.protocol.packet.Packet;
import io.netty.channel.Channel;

/**
 * Shutdown the container, because the Open-Master cant verified it
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public class UnknownContainerConnectionInPacket implements Packet {

	/**
	 * {@inheritDoc}
	 */
	public final Packet process(final Channel channel) {
		Logger.warn("Open-Master does not know this Open-Container!");
		Logger.info("Please create a Open-Container at the Open-Master!");
		Container.getContainer().shutdown();
		return null;
	}

}
