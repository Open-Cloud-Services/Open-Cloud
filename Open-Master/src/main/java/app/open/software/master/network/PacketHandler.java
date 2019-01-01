/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.network;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.ServiceCluster;
import app.open.software.master.container.ContainerEntity;
import app.open.software.master.container.ContainerEntityService;
import app.open.software.master.network.packets.ContainerKeyValidationInPacket;
import app.open.software.master.network.packets.connection.UnknownContainerConnectionOutPacket;
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
	 * Invoked if a {@link Channel} from a {@link ProtocolClient} is active and a {@link ContainerEntity} wants to connect
	 *
	 * @param ctx {@link ChannelHandlerContext} from netty
	 */
	public void channelActive(final ChannelHandlerContext ctx) {
		final ContainerEntity containerEntity = ServiceCluster.get(ContainerEntityService.class).getContainerByChannel(ctx.channel());
		if (containerEntity != null) {
			Logger.info("Container wants to connect, waiting for validation!");
		} else {
			ctx.writeAndFlush(new UnknownContainerConnectionOutPacket()).addListener(ChannelFutureListener.CLOSE);
			Logger.warn("Unknown container tried to connect!");
		}
	}

	/**
	 * Read {@link Packet} and send response if available
	 *
	 * @param ctx {@link ChannelHandlerContext} from netty
	 * @param packet {@link Packet} to process
	 */
	protected void channelRead0(final ChannelHandlerContext ctx, final Packet packet) {
		final ContainerEntity containerEntity = ServiceCluster.get(ContainerEntityService.class).getContainerByChannel(ctx.channel());
		if (containerEntity.isVerified() || packet instanceof ContainerKeyValidationInPacket) {
			final Packet response = packet.process(ctx.channel());
			if (response != null) ctx.writeAndFlush(response);
		} else {
			ctx.writeAndFlush(new UnknownContainerConnectionOutPacket()).addListener(ChannelFutureListener.CLOSE);
			Logger.warn("Received packet from unknown container!");
		}
	}

}
