/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.setup.request.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

/**
 * A wrapper for the {@link ReadableByteChannel} to have the possibility, to show a progress bar of a download
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
@RequiredArgsConstructor
public class ReadableByteChannelWrapper implements ReadableByteChannel {

	/**
	 * {@link ReadableByteChannel} which is wrapped
	 */
	private final ReadableByteChannel readableByteChannel;

	/**
	 * {@link Consumer} to get a callback with the current state of progress
	 */
	private final Consumer<Long> callback;

	/**
	 * Current downloaded size of the file
	 */
	private long currentSize;

	/**
	 * Read bytes from a stream and added the read bytes to the current size, to show the actual progress
	 *
	 * @param dst The buffer into which bytes are to be transferred
	 * @return The number of bytes read, possibly zero, or -1 if the channel has reached end-of-stream
	 *
	 * @throws IOException Error while reading from the {@link ByteBuffer}
	 */
	public int read(final ByteBuffer dst) throws IOException {
		int read;

		if ((read = this.readableByteChannel.read(dst)) > 0) {
			this.currentSize += read;
			this.callback.accept(this.currentSize);
		}

		return read;
	}

	/**
	 * @return True, if the {@link ReadableByteChannel} is not closed
	 */
	public boolean isOpen() {
		return this.readableByteChannel.isOpen();
	}

	/**
	 * Close the {@link ReadableByteChannel}
	 *
	 * @throws IOException If an I/O error occurs
	 */
	public void close() throws IOException {
		this.readableByteChannel.close();
	}

}
