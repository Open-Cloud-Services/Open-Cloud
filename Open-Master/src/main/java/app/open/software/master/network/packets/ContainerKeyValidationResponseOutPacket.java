/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.network.packets;

import app.open.software.protocol.packet.Packet;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

/**
 * Response of the {@link ContainerKeyValidationInPacket}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
@RequiredArgsConstructor
public class ContainerKeyValidationResponseOutPacket implements Packet {

	/**
	 * Response of the authentication
	 */
	private final boolean verified;

	/**
	 * {@inheritDoc}
	 */
	public void write(final ByteBufOutputStream byteBuf) throws IOException {
		byteBuf.writeBoolean(this.verified);
	}

}
