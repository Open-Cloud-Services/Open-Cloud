/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.network.packets;

import app.open.software.container.Container;
import app.open.software.core.logger.Logger;
import app.open.software.protocol.packet.Packet;
import app.open.software.protocol.packet.impl.SuccessPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.Channel;
import java.io.IOException;

/**
 * Response of the {@link ContainerKeyValidationOutPacket}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public class ContainerKeyValidationResponseInPacket implements Packet {

	/**
	 * This {@link Container} is verified
	 */
	private boolean verified;

	/**
	 * {@inheritDoc}
	 */
	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.verified = byteBuf.readBoolean();
	}

	/**
	 * {@inheritDoc}
	 */
	public final Packet process(final Channel channel) {
		if (!this.verified) {
			Logger.warn("Validation key is wrong!");
			Logger.info("Please configure the key in the config!");
			Container.getContainer().shutdown();
			return null;
		} else {
			Logger.info("Verified by Open-Master!");
		}

		return new SuccessPacket();
	}

}
