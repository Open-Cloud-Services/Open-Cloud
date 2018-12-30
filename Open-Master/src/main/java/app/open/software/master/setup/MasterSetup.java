/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.setup;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.ServiceCluster;
import app.open.software.core.setup.request.DownloadRequest;
import app.open.software.core.setup.request.impl.ListRequest;
import app.open.software.core.setup.request.impl.StringRequest;
import app.open.software.master.container.ContainerEntityService;
import app.open.software.master.setup.version.ProxyVersion;
import app.open.software.master.setup.version.ServerVersion;
import java.io.*;
import java.nio.file.Files;

/**
 * Setup to configure the setup of the Open-Master
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
public class MasterSetup {

	/**
	 * {@inheritDoc}
	 */
	public void setup(final BufferedReader reader) throws IOException {
		if (!this.setupIsNeeded()) return;

		Logger.info("Welcome to the setup!");

		this.setupProxyJar(reader);

		this.setupServerJar(reader);

		if (ServiceCluster.get(ContainerEntityService.class).getContainerEntities().isEmpty()) {
			Logger.info("To create a container use the following command: \"container create <host>\"!");
		}

		Logger.info("Setup completed!");
	}

	/**
	 * Setup the Proxy jar file
	 *
	 * @param reader {@link BufferedReader} to read user input
	 *
	 * @throws IOException An I/O error occurred
	 */
	private void setupProxyJar(final BufferedReader reader) throws IOException {
		final File proxyTemplate = new File("proxy");

		if (Files.notExists(proxyTemplate.toPath())) {
			Files.createDirectories(proxyTemplate.toPath());
		}

		final File proxyJar = new File("proxy//proxy.jar");
		if (Files.notExists(proxyJar.toPath())) {
			new ListRequest("Which proxy version you want to install?", reader).request(ProxyVersion.values(), version -> {
				final ProxyVersion install = ProxyVersion.valueOf(version.toUpperCase());
				try {
					Logger.info(install.getUrl());
					new DownloadRequest().request(install.getUrl(), proxyJar.getPath(), () -> Logger.info("Download complete!"));
				} catch (IOException e) {
					Logger.error("Download of proxy software failed!", e);
				}
			});
		}
	}

	/**
	 * Setup the Server jar file
	 *
	 * @param reader {@link BufferedReader} to read user input
	 *
	 * @throws IOException An I/O error occurred
	 */
	private void setupServerJar(final BufferedReader reader) throws IOException{
		final File globalTemplate = new File("global");

		if (Files.notExists(globalTemplate.toPath())) {
			Files.createDirectories(globalTemplate.toPath());
		}

		final File serverJar = new File("global//server.jar");
		if (Files.notExists(serverJar.toPath())) {
			new ListRequest("Which server version you want to install?", reader).request(ServerVersion.values(), version -> {
				final ServerVersion install = ServerVersion.valueOf(version.toUpperCase());
				Logger.info(install.toString());

				if (install == ServerVersion.CUSTOM) {
					this.downloadCustomServerSoftware(serverJar.getPath(), reader);
				} else {
					try {
						new ListRequest("Which specific version you want to install?", reader).request(install.getVersions(), specificVersion -> {
							final var url = install.getUrl().replace("%version%", install.getVersionByName(specificVersion).getVersion());
							Logger.info(url);
							try {
								new DownloadRequest().request(url, serverJar.getPath(), () -> Logger.info("Download complete!"));
							} catch (IOException e) {
								Logger.error("Download of server software failed!", e);
							}
						});
					} catch (IOException e) {
						Logger.error("Reading of input failed!", e);
					}
				}
			});
		}
	}

	/**
	 * Download a custom server software
	 *
	 * @param path Path where the server software will be located
	 * @param reader {@link BufferedReader} to read user input
	 */
	private void downloadCustomServerSoftware(final String path, final BufferedReader reader) {
		try {
			new StringRequest("Type in your custom url for your server software:", reader).request(url -> {
				if (!url.endsWith(".jar")) {
					Logger.warn("This url is not valid! Please make sure, that your url ends with .jar!");
					this.downloadCustomServerSoftware(path, reader);
				} else {
					try {
						new DownloadRequest().request(url, path, () -> Logger.info("Download complete!"));
					} catch (IOException e) {
						Logger.error("Download of server software failed!", e);
					}
				}
			});
		} catch (IOException e) {
			Logger.error("Reading of input failed!", e);
		}
	}

	/**
	 * @return If setup is needed
	 */
	private boolean setupIsNeeded() {
		return !(new File("proxy//proxy.jar").exists()) || !(new File("global//server.jar").exists());
	}

}
