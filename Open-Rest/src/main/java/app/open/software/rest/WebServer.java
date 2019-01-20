/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.rest;

import app.open.software.core.logger.Logger;
import app.open.software.rest.version.RestVersion;
import java.util.*;
import lombok.RequiredArgsConstructor;

import org.eclipse.jetty.http.HttpStatus;
import spark.*;
import static spark.Spark.*;

/**
 * Web server to run the rest api
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6
 */
@RequiredArgsConstructor
public class WebServer {

	/**
	 * Port of the web server
	 */
	private final int port;

	/**
	 * {@link List} of all {@link RestVersion}s
	 */
	private final List<RestVersion> versions = new ArrayList<>();

	/**
	 * Start the web server
	 */
	public void start(final Filter auth) {
		port(this.port);

		this.initWebServer(auth);
		awaitInitialization();

		Logger.info("Successfully bound web server to port " + this.port);
	}

	/**
	 * Init the web server
	 */
	private void initWebServer(final Filter auth) {
		exception(Exception.class, (exception, request, response) -> {
			Logger.error(exception.getMessage(), exception);
		});

		before("/*", (request, response) -> {
			final String path = request.pathInfo();
			if (path.endsWith("/")) {
				response.redirect(path.substring(0, path.length() - 1), HttpStatus.MOVED_PERMANENTLY_301);
			}
		});

		before("/*", auth);

		this.versions.forEach(RestVersion::init);
	}

	/**
	 * Register {@link RestVersion}s
	 *
	 * @param restVersions Varargs of {@link RestVersion}s to register
	 */
	public void registerVersions(final RestVersion... restVersions) {
		this.versions.addAll(Arrays.asList(restVersions));
	}

	/**
	 * Stop the web server
	 */
	public void stop() {
		Spark.stop();
		Logger.info("Stopped web server!");
	}

}
