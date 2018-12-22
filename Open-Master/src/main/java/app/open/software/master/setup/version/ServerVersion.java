/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.setup.version;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum of all supported server software with all supported specific versions
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
@Getter
@RequiredArgsConstructor
public enum ServerVersion {

	SPIGOT("https://cdn.getbukkit.org/spigot/%version%.jar", new Version[]{
			new Version("1.7.10", "spigot-1.7.10-SNAPSHOT-b1657"),
			new Version("1.8.8", "spigot-1.8.8-R0.1-SNAPSHOT-latest"),
			new Version("1.9.4", "spigot-1.9.4-R0.1-SNAPSHOT-latest"),
			new Version("1.10.2", "spigot-1.10.2-R0.1-SNAPSHOT-latest"),
			new Version("1.11.2", "spigot-1.11.2"),
			new Version("1.12.2", "spigot-1.12.2"),
			new Version("1.13.2", "spigot-1.13.2")
	}),

	PAPERSPIGOT("https://papermc.io/ci/job/%version%.jar", new Version[]{
			new Version("1.8.8", "Paper/443/artifact/paperclip-1.8.8-fix"),
			new Version("1.9.4", "Paper/773/artifact/paperclip-773"),
			new Version("1.10.2", "Paper/916/artifact/paperclip-916.2"),
			new Version("1.11.2", "Paper/1104/artifact/paperclip"),
			new Version("1.12.2", "Paper/lastStableBuild/artifact/paperclip"),
			new Version("1.13", "Paper-1.13/lastSuccessfulBuild/artifact/paperclip-487")
	}),


	CUSTOM("", null);

	/**
	 * Main url for downloading the server software
	 */
	private final String url;

	/**
	 * Array of {@link Version}s for all supported specific versions
	 */
	private final Version[] versions;

	/**
	 * @param name Name of the specific server software
	 *
	 * @return {@link Version} object by name
	 */
	public final Version getVersionByName(final String name) {
		return Arrays.stream(this.versions).filter(version -> version.toString().equalsIgnoreCase(name)).findFirst().orElse(null);
	}

	/**
	 * @return Name of the server software in the right case
	 */
	public final String toString() {
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}

}
