/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.network.packets;

import app.open.software.container.Container;
import app.open.software.protocol.packet.Packet;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

/**
 * Send key to the Open-Master to authenticate this {@link Container}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
@RequiredArgsConstructor
public class ContainerKeyValidationOutPacket implements Packet {

	/**
	 * Key of this {@link Container} to validate
	 */
	private final String key;

	/**
	 * {@inheritDoc}
	 */
	public void write(final ByteBufOutputStream byteBuf) throws IOException {
		byteBuf.writeUTF(this.key);
	}

}
