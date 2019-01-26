/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.container;

import java.util.UUID;
import lombok.Data;

/**
 * Container meta entity
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6.1
 */
@Data
public class ContainerMeta {

	/**
	 * Unique id of the container
	 */
	private final UUID uuid;

	/**
	 * Host of the container
	 */
	private final String host;

	/**
	 * Port of the container
	 */
	private final int port;

	/**
	 * Token for validation
	 */
	private final String token;

}
