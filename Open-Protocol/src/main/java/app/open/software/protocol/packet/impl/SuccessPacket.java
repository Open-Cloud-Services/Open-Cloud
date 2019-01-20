/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol.packet.impl;

import app.open.software.protocol.packet.Packet;
import io.netty.channel.Channel;

public class SuccessPacket implements Packet {

	public final Packet process(final Channel channel) {
		return null;
	}

}
