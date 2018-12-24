/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
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
	private EventLoopGroup workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

	/**
	 * {@link ChannelFuture} for disconnecting later
	 */
	private ChannelFuture channelFuture;

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
	public final ProtocolClient connect(final Runnable complete, final ChannelInitializer<Channel> initializer) {
		try {
			this.channelFuture = new Bootstrap()
					.group(this.workerGroup)
					.channel(this.EPOLL ? EpollSocketChannel.class : NioSocketChannel.class)
					.handler(initializer)
					.connect(this.networkConnectionEntity.getHost(), this.networkConnectionEntity.getPort())
					.sync();

			complete.run();

			this.channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (this.workerGroup != null) {
				this.workerGroup.shutdownGracefully().syncUninterruptibly();
			}
		}

		return this;
	}

	/**
	 * Disconnect from a {@link ProtocolServer}
	 *
	 * @param disconnected {@link Runnable} which will executed when the {@link ProtocolClient} is disconnected
	 *
	 * @throws InterruptedException An error occurred
	 */
	public void disconnect(final Runnable disconnected) throws InterruptedException {
		if (this.channelFuture != null) {
			this.channelFuture.channel().closeFuture().sync();
		}

		if (this.workerGroup != null) {
			this.workerGroup.shutdownGracefully().sync();
		}
	}

}
