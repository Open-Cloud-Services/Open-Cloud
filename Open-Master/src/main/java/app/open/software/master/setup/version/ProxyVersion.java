/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.setup.version;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum of all supported proxy versions
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
@RequiredArgsConstructor
public enum ProxyVersion {

	BUNGEECORD("https://ci.md-5.net/job/BungeeCord/lastStableBuild/artifact/bootstrap/target/BungeeCord.jar"),
	WATERFALL("https://papermc.io/ci/job/Waterfall/lastStableBuild/artifact/Waterfall-Proxy/bootstrap/target/Waterfall.jar"),
	TRAVERTINE("https://papermc.io/ci/job/Travertine/lastStableBuild/artifact/Travertine-Proxy/bootstrap/target/Travertine.jar");

	/**
	 * Url to download the proxy jar
	 */
	@Getter
	private final String url;

	/**
	 * @return Name of the proxy in the right case
	 */
	public final String toString() {
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}

}
