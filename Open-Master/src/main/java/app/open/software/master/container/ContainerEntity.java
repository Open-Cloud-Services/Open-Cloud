/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.container;

import app.open.software.master.network.packets.connection.MasterTerminateConnectionOutPacket;
import app.open.software.protocol.packet.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

/**
 * Entity for an Open-Container object
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
@Getter
@RequiredArgsConstructor
public class ContainerEntity {

	/**
	 * {@link ContainerMeta} to get the saved information about the container
	 */
	private final ContainerMeta containerMeta;

	/**
	 * {@link Channel} to send packets to the container
	 */
	@Setter
	private Channel channel;

	/**
	 * Container is verified in the network system
	 */
	@Setter
	private boolean verified = false;

	/**
	 * {@link List} of {@link Packet}s, which will send after the channel is active
	 */
	private final List<Packet> packetQueue = new ArrayList<>();

	/**
	 * Send a {@link Packet} to the container
	 *
	 * @param packet {@link Packet} which will be send
	 */
	public void sendPacket(final Packet packet) {
		if (this.channel == null) {
			this.packetQueue.add(packet);
		} else {
			this.channel.writeAndFlush(packet);
		}
	}

	/**
	 * Terminate the connection to the container
	 *
	 * @throws InterruptedException An error occurred
	 */
	public void disconnect() throws InterruptedException {
		if (this.channel != null) {
			this.channel.writeAndFlush(new MasterTerminateConnectionOutPacket()).addListener(ChannelFutureListener.CLOSE).sync();
		}
	}

	/**
	 * @return If the container is connected to this Open-Master
	 */
	public final boolean isConnected() {
		return this.channel != null && this.channel.isOpen();
	}

	/**
	 * {@inheritDoc}
	 */
	public final String toString() {
		return "Container @" + this.containerMeta.getHost() + " is " + (this.isConnected() ? "connected" : "not connected");
	}

}