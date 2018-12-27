/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol;

import app.open.software.core.logger.Logger;
import app.open.software.core.thread.ThreadBuilder;
import app.open.software.protocol.packet.Packet;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.ConnectException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@link ProtocolClient} to connect to a {@link ProtocolServer}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
@RequiredArgsConstructor
public class ProtocolClient {

	/**
	 * {@link NetworkConnectionEntity} to connect to a {@link ProtocolServer}
	 */
	private final NetworkConnectionEntity networkConnectionEntity;

	/**
	 * {@link EventLoopGroup} to manage {@link Thread}s
	 */
	private final EventLoopGroup workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

	/**
	 * {@link Channel} for disconnecting later
	 */
	private Channel channel;

	/**
	 * {@link Queue} to store all {@link Packet}s, which can not be sent, because the {@link Channel} is not active
	 */
	@Getter
	private final Queue<Packet> packetQueue = new ConcurrentLinkedQueue<>();

	/**
	 * Epoll is available
	 */
	private final boolean EPOLL = Epoll.isAvailable();

	/**
	 * Connect to a {@link ProtocolServer}
	 *
	 * @param complete {@link Runnable} which will be executed after the {@link ProtocolClient} is connected
	 * @param initializer {@link ChannelInitializer} to configure the {@link ChannelPipeline}
	 *
	 * @return Current instance
	 */
	public final ProtocolClient connect(final Runnable complete, final Runnable failed, final ChannelInitializer<Channel> initializer) {
		new ThreadBuilder("Protocol-Client", () -> {

			try {
				this.channel = new Bootstrap()
						.group(this.workerGroup)
						.channel(this.EPOLL ? EpollSocketChannel.class : NioSocketChannel.class)
						.handler(initializer)
						.connect(this.networkConnectionEntity.getHost(), this.networkConnectionEntity.getPort())
						.addListener((ChannelFutureListener) future -> {
							if (future.isSuccess()) {
								complete.run();
							} else {
								failed.run();
							}
						})
						.sync()
						.channel();

				this.channel.closeFuture().sync();
			} catch (Exception e) {
				if (!(e instanceof ConnectException)) {
					Logger.error("Could not connect to the Open-Master", e);
				}
			} finally {
				if (this.workerGroup != null) {
					this.workerGroup.shutdownGracefully().syncUninterruptibly();
				}
			}

		}).start();

		return this;
	}

	/**
	 * Send a {@link Packet} to the Open-Master
	 *
	 * @param packet {@link Packet} instance
	 */
	public void sendPacket(final Packet packet) {
		if (this.channel == null) {
			this.packetQueue.offer(packet);
		} else {
			this.channel.writeAndFlush(packet);
		}
	}

	/**
	 * @return If the {@link ProtocolClient} is connected
	 */
	public final boolean isConnected() {
		return this.channel != null && this.channel.isOpen();
	}

	/**
	 * Disconnect from a {@link ProtocolServer}
	 *
	 * @param disconnected {@link Runnable} which will executed when the {@link ProtocolClient} is disconnected
	 *
	 */
	public void disconnect(final Runnable disconnected) {
		if (this.channel != null && this.channel.isOpen()) {
			this.channel.close().syncUninterruptibly();
		}

		disconnected.run();
	}

}
