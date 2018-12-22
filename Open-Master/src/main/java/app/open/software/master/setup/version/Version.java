/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.setup.version;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represent a server software version
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
@Getter
@RequiredArgsConstructor
public class Version {

	/**
	 * Name or version number of the server software
	 */
	private final String name;

	/**
	 * Specific build version
	 */
	private final String version;

	/**
	 * @return Name of the version
	 */
	public final String toString() {
		return this.name;
	}

}
