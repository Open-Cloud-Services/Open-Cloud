/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol;

import app.open.software.core.logger.Logger;
import app.open.software.core.thread.ThreadBuilder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.BindException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@link ProtocolServer} to receive connections from {@link ProtocolClient}s
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
@RequiredArgsConstructor
public class ProtocolServer {

	/**
	 * Port to bind
	 */
	private final int port;

	/**
	 * Epoll is available
	 */
	private final boolean EPOLL = Epoll.isAvailable();

	/**
	 * {@link EventLoopGroup}s to manage {@link Thread}s
	 */
	private EventLoopGroup bossGroup, workerGroup;

	/**
	 * {@link ChannelFuture} for shutdown this {@link ProtocolServer}
	 */
	private ChannelFuture channelFuture;

	/**
	 * True if this server was bounded correctly to the port
	 */
	@Getter
	private boolean active = false;

	/**
	 * Bind this {@link ProtocolServer} on a port
	 *
	 * @param success {@link Runnable} which will be executed after the {@link ProtocolServer} was bind
	 * @param failed {@link Runnable} which will be executed if something failed
	 * @param initializer {@link ChannelInitializer} to configure the {@link ChannelPipeline}
	 * @return Current instance
	 */
	public final ProtocolServer bind(final Runnable success, final Runnable failed, final ChannelInitializer<Channel> initializer) {

		this.bossGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
		this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

		new ThreadBuilder("Protocol-Server", () -> {

			try {
				this.channelFuture = new ServerBootstrap()
						.group(this.bossGroup, this.workerGroup)
						.channel(this.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
						.childHandler(initializer)
						.bind(this.port)
						.addListener((ChannelFutureListener) future -> {
							if (future.isSuccess()) {
								success.run();
								ProtocolServer.this.active = true;
							}
						}).sync();

				this.channelFuture.channel().closeFuture().sync();
			} catch (Exception e) {
				if (!(e instanceof BindException)) {
					Logger.error("Interrupted while binding server to the port!", e);
				} else {
					Logger.warn("Can not bind to the server port!");
				}

				failed.run();
			} finally {
				if (this.bossGroup != null) {
					this.bossGroup.shutdownGracefully().syncUninterruptibly();
				}

				if (this.workerGroup != null) {
					this.workerGroup.shutdownGracefully().syncUninterruptibly();
				}
			}

		}).setDaemon().start();

		return this;
	}

	/**
	 * Shutdown this {@link ProtocolServer}
	 *
	 * @param closed {@link Runnable} which will executed when this {@link ProtocolServer} was closed
	 *
	 * @throws InterruptedException An error occurred
	 */
	public void shutdown(final Runnable closed) throws InterruptedException {
		if (this.channelFuture != null) {
			this.channelFuture.channel().close().sync();
		}

		if (this.bossGroup != null) {
			this.bossGroup.shutdownGracefully().sync();
		}

		if (this.workerGroup != null) {
			this.workerGroup.shutdownGracefully().sync();
		}

		closed.run();
	}

}
