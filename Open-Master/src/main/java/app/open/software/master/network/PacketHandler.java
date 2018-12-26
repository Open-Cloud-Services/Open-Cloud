package app.open.software.master.network;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.ServiceCluster;
import app.open.software.master.container.ContainerEntity;
import app.open.software.master.container.ContainerEntityService;
import app.open.software.protocol.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

	public void channelActive(final ChannelHandlerContext ctx) {
		final ContainerEntity containerEntity = ServiceCluster.get(ContainerEntityService.class).getContainerByChannel(ctx.channel());
		if (containerEntity != null) {
			containerEntity.setChannel(ctx.channel());
			containerEntity.getPacketQueue().forEach(containerEntity::sendPacket);
			containerEntity.getPacketQueue().clear();
		} else {
			Logger.warn("Unknown container tries to connect!");
		}
	}

	protected void channelRead0(final ChannelHandlerContext ctx, final Packet packet) {
		final Packet response = packet.process(ctx.channel());
		if(response != null) ctx.writeAndFlush(packet);
	}

	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		final ContainerEntity containerEntity = ServiceCluster.get(ContainerEntityService.class).getContainerByChannel(ctx.channel());
		if (containerEntity != null) {
			containerEntity.setChannel(null);
			Logger.info("Container from " + containerEntity.getContainerMeta().getHost() + " disconnected!");
		}
		super.channelInactive(ctx);
	}

	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

}
