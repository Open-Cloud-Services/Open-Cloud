/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.updater;

import app.open.software.core.logger.Logger;
import app.open.software.core.setup.request.DownloadRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * Automatic updates for Open-Cloud
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
@RequiredArgsConstructor
public class AutoUpdater {

	/**
	 * Current running version
	 */
	private final String currentVersion;

	/**
	 * Type of update
	 */
	private final UpdateType updateType;

	/**
	 * Checking if a new update is available
	 *
	 * @return True if an update is available
	 */
	public boolean checkForUpdate() {
		if (this.currentVersion.equals("Dev-Version")) return false;

		Logger.info("Checking for a new update...");

		final var request = HttpRequest
				.newBuilder(
						URI.create("https://api.github.com/repos/Open-Cloud-Services/Open-Cloud/releases/latest"))
				.GET()
				.build();

		try {
			final var response = HttpClient
					.newBuilder()
					.build()
					.send(request, HttpResponse.BodyHandlers.ofString());

			final var object = (JsonObject) new JsonParser().parse(response.body());
			final var latestVersion = String.valueOf(object.get("tag_name")).replace("\"", "");

			if (!this.currentVersion.equals(latestVersion)) {
				Logger.info("A new update [" + latestVersion + "] is available!");
				Logger.info("Starting update...");

				this.update(object);
				return true;
			}

			Logger.info("No update available!");

			return false;
		} catch (IOException | InterruptedException e) {
			Logger.error("Checking for update was failed!", e);
		}

		return false;
	}

	/**
	 * Download and install the update
	 *
	 * @param object {@link JsonObject} with information about the download url
	 */
	private void update(final JsonObject object) {
		final var assets = object.get("assets").getAsJsonArray();
		final var downloadUrl = assets.get(0).getAsJsonObject().get("browser_download_url").getAsString();

		try {
			new DownloadRequest().request(downloadUrl, "update.zip", () -> {
				try {
					this.installUpdate();
					this.clearUp();
					Logger.info("Update complete!");
				} catch (IOException | ZipException e) {
					Logger.error("Could not install update!", e);
				}
			});

		} catch (IOException e) {
			Logger.error("Could not download update!", e);
		}
	}

	/**
	 * Install the update
	 *
	 * @throws ZipException Extracting fails
	 */
	private void installUpdate() throws ZipException {
		Logger.info("Install update...");

		final var zipFile = new ZipFile("update.zip");
		switch (this.updateType) {

			case CONTAINER:
				Logger.info("Extract update...");
				zipFile.extractFile("Open-Container.jar", Paths.get("").toAbsolutePath().toString());
				break;

			case MASTER:
				Logger.info("Extract update...");
				zipFile.extractFile("Open-Master.jar", Paths.get("").toAbsolutePath().toString());
				break;
		}
	}

	/**
	 * Clear all unnecessary files up
	 *
	 * @throws IOException An I/O error occurred
	 */
	private void clearUp() throws IOException {
		final var zip = new File("update.zip");

		if (Files.exists(zip.toPath())) {
			Files.delete(zip.toPath());
		}
	}

}
