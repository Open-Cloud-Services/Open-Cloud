/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.network;

import app.open.software.container.Container;
import app.open.software.protocol.ProtocolClient;
import app.open.software.protocol.packet.Packet;
import io.netty.channel.*;

/**
 * Handle {@link Packet}s and {@link Channel} states
 *
 * @author Tammo0987
 * @version 1.1
 * @since 0.4
 */
public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

	/**
	 * Invoked if the {@link Channel} from the {@link ProtocolClient} to the Open-Master is active
	 *
	 * @param ctx {@link ChannelHandlerContext} from netty
	 */
	public void channelActive(final ChannelHandlerContext ctx) {
		Container.getContainer().getProtocolClient().getPacketQueue().forEach(packet -> Container.getContainer().getProtocolClient().sendPacket(packet));
		Container.getContainer().getProtocolClient().getPacketQueue().clear();
	}

	/**
	 * Read {@link Packet} and send response if available
	 *
	 * @param ctx {@link ChannelHandlerContext} from netty
	 * @param packet {@link Packet} to process
	 */
	protected void channelRead0(final ChannelHandlerContext ctx, final Packet packet) {
		final Packet response = packet.process(ctx.channel());
		if (response != null) ctx.writeAndFlush(response);
	}

}
