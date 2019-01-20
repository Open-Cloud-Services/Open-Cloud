/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol.handler;

import app.open.software.protocol.packet.Packet;
import app.open.software.protocol.packet.registry.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * Decoding {@link Packet}s
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public class PacketDecoder extends ByteToMessageDecoder {

	/**
	 * {@inheritDoc}
	 */
	protected void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) throws Exception {
		final int id = in.readInt();
		final Packet packet = PacketRegistry.IN.getPacketById(id);

		packet.read(new ByteBufInputStream(in));
		out.add(packet);
	}

}
