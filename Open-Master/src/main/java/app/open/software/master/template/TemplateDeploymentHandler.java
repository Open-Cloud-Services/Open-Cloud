/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.template;

import app.open.software.rest.WebServer;
import app.open.software.rest.handler.RestHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.eclipse.jetty.http.HttpStatus;
import static spark.Spark.*;

/**
 * Handle the all templates for the {@link WebServer}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6
 */
public class TemplateDeploymentHandler implements RestHandler {

	/**
	 * Register all the routes from this handler
	 */
	public void route() {
		get("/template/deployment", (request, response) -> {
			if (request.headers("type") == null) {
				response.status(HttpStatus.BAD_REQUEST_400);
				return "";
			}

			final var tempFile = new File("temp");

			if (Files.notExists(tempFile.toPath())) {
				Files.createDirectories(tempFile.toPath());
			}

			final var type = request.headers("type");

			switch (type.toLowerCase()) {
				case "global":

					final var globalDir = new File("global");
					if (Files.notExists(globalDir.toPath())) {
						response.status(HttpStatus.NOT_FOUND_404);
						return "";
					}

					this.zip(globalDir, "temp/global");

					final var globalZip = new File("temp/global.zip");
					if (Files.notExists(globalZip.toPath())) {
						response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
						return "";
					}

					response.raw().setContentType("application/octet-stream");
					response.raw().setHeader("Content-Disposition","attachment; filename=global.zip");

					try (final BufferedOutputStream outputStream = new BufferedOutputStream(response.raw().getOutputStream())) {
						outputStream.write(Files.readAllBytes(globalZip.toPath()));
					}

					return response.raw();

				case "proxy":

					final var proxyDir = new File("proxy");

					if (Files.notExists(proxyDir.toPath())) {
						response.status(HttpStatus.NOT_FOUND_404);
						return "";
					}

					this.zip(proxyDir, "temp/proxy");

					final var proxyZip = new File("temp/proxy.zip");
					if (Files.notExists(proxyZip.toPath())) {
						response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
						return "";
					}

					try (final BufferedOutputStream outputStream = new BufferedOutputStream(response.raw().getOutputStream())) {
						outputStream.write(Files.readAllBytes(proxyZip.toPath()));
					}

					return response.raw();

				case "server":

					if (request.headers("group") == null) {
						response.status(HttpStatus.BAD_REQUEST_400);
						return "";
					}

					final var group = request.headers("group");
					final var templateDir = new File("template//" + group);

					if (Files.notExists(templateDir.toPath())) {
						response.status(HttpStatus.NOT_FOUND_404);
						return "";
					}

					this.zip(templateDir, "temp/" + group);

					final var templateZip = new File("temp/" + group + ".zip");
					if (Files.notExists(templateZip.toPath())) {
						response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);
						return "";
					}

					try (final BufferedOutputStream outputStream = new BufferedOutputStream(response.raw().getOutputStream())) {
						outputStream.write(Files.readAllBytes(templateZip.toPath()));
					}

					return response.raw();
			}

			return "";
		});

	}

	/**
	 * Put a directory into an zip archive
	 *
	 * @param dir Directory which should put in the archive
	 * @param zipPath Path to the zip file
	 * @throws ZipException The packaging of the zip archive has failed
	 */
	private void zip(final File dir, final String zipPath) throws ZipException {
		final var zipFile = new ZipFile(zipPath + ".zip");
		final var parameters = new ZipParameters();

		parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

		Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach(file -> {
			try {
				if (file.isDirectory()) {
					zipFile.addFolder(file, parameters);
				} else {
					zipFile.addFile(file, parameters);
				}
			} catch (ZipException e) {
				e.printStackTrace();
			}
		});
	}

}
