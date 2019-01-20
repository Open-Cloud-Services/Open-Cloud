/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.setup.request;

import app.open.software.core.exception.FileDownloadException;
import app.open.software.core.logger.Logger;
import app.open.software.core.logger.component.impl.ProgressBarComponent;
import app.open.software.core.setup.request.util.ReadableByteChannelWrapper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Download a file from a remote host
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
public class DownloadRequest {

	/**
	 * Create a connection and downloading the file from the requested url
	 *
	 * @param url Url route from the file
	 * @param path Path where the file should be located
	 * @param complete {@link Runnable} which will be executed after finishing
	 *
	 * @throws IOException An I/O error occurred
	 */
	public void request(final String url, final String path, final Runnable complete) throws IOException {
		final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
			final var progressBarComponent = new ProgressBarComponent(connection.getContentLength());
			Logger.progress(progressBarComponent);

			try (final ReadableByteChannel channel = new ReadableByteChannelWrapper(Channels.newChannel(connection.getInputStream()), progressBarComponent::updateProgress)) {
				try (final FileOutputStream fileOutputStream = new FileOutputStream(path)) {
					fileOutputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
				}
			}

			complete.run();
		} else {
			Logger.error("Unable to download file from the requested url!", new FileDownloadException("Unable to download file from the requested url!"));
		}

		connection.disconnect();
	}

}
