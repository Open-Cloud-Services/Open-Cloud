/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import java.io.IOException;

/**
 * Packet interface, to implement different types of {@link Packet}s
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public interface Packet {

	/**
	 * Read values from the {@link Packet}
	 *
	 * @param byteBuf {@link ByteBufInputStream} to read from
	 *
	 * @throws IOException An I/O error occurred
	 */
	default void read(final ByteBufInputStream byteBuf) throws IOException {}

	/**
	 * Write values in the {@link Packet}
	 *
	 * @param byteBuf {@link ByteBufOutputStream} to write into
	 *
	 * @throws IOException An I/O error occurred
	 */
	default void write(final ByteBufOutputStream byteBuf) throws IOException {}

	/**
	 * Process the {@link Packet}
	 *
	 * @param channel {@link Channel} where the {@link Packet} was send
	 *
	 * @return Response {@link Packet}
	 */
	Packet process(final Channel channel);

}
