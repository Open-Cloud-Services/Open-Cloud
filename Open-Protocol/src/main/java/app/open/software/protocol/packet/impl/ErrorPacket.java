/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol.packet.impl;

import app.open.software.protocol.packet.Packet;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import java.io.IOException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorPacket implements Packet {

	private String error;

	public void read(final ByteBufInputStream byteBuf) throws IOException {
		this.error = byteBuf.readUTF();
	}

	public void write(final ByteBufOutputStream byteBuf) throws IOException {
		byteBuf.writeUTF(this.error);
	}

}
