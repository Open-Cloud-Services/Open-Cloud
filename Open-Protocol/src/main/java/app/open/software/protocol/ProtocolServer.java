/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
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
	private EventLoopGroup bossGroup = this.declareEventLoopGroup(), workerGroup = this.declareEventLoopGroup();

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
		new ThreadBuilder("Protocol-Server", () -> {

			try {
				final var serverBootstrap = this.configureServerBootstrap();

				this.channelFuture = serverBootstrap
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
				try {
					this.close();
				} catch (InterruptedException e) {
					Logger.error("Error while closing server", e);
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
		this.close();

		closed.run();
	}

	/**
	 * Close the {@link ProtocolServer}
	 *
	 * @throws InterruptedException An error occurred
	 */
	private void close() throws InterruptedException{
		if (this.channelFuture != null && this.channelFuture.channel().isOpen()) {
			this.channelFuture.channel().close().sync();
		}

		if (this.bossGroup != null) {
			this.bossGroup.shutdownGracefully().sync();
		}

		if (this.workerGroup != null) {
			this.workerGroup.shutdownGracefully().sync();
		}
	}

	/**
	 * @return Configured {@link ServerBootstrap}
	 */
	private ServerBootstrap configureServerBootstrap() {
		return new ServerBootstrap().group(this.bossGroup, this.workerGroup).channel(this.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class);
	}

	/**
	 * @return Configured {@link EventLoopGroup}
	 */
	private EventLoopGroup declareEventLoopGroup() {
		return this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
	}

}
