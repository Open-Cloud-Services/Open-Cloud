/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol.handler;

import app.open.software.protocol.packet.Packet;
import app.open.software.protocol.packet.registry.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Encoding {@link Packet}s
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

	/**
	 * {@inheritDoc}
	 */
	protected void encode(final ChannelHandlerContext ctx, final Packet packet, final ByteBuf out) throws Exception {
		final int id = PacketRegistry.OUT.getIdByPacket(packet);

		if (id == -1) {
			throw new NullPointerException("Could not get id from packet " + packet.getClass().getSimpleName() + "!");
		} else {
			out.writeInt(id);
			packet.write(new ByteBufOutputStream(out));
		}
	}

}
