package app.open.software.container.network;

import app.open.software.core.logger.Logger;
import app.open.software.protocol.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class PacketHandler extends SimpleChannelInboundHandler<Packet> {

	public void channelActive(final ChannelHandlerContext ctx) {
		//Logger.info("Successfully connected to Open-Master!");
	}

	protected void channelRead0(final ChannelHandlerContext ctx, final Packet packet) {
		final Packet response = packet.process(ctx.channel());
		if (response != null) ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
		Logger.info("Caught");
		super.exceptionCaught(ctx, cause);
	}
}
