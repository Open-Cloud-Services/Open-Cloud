/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol;

import lombok.Value;

/**
 * Entity for a host and a port
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
@Value
public class NetworkConnectionEntity {

	/**
	 * Host of the address
	 */
	private final String host;

	/**
	 * Port of the address
	 */
	private final int port;

}
