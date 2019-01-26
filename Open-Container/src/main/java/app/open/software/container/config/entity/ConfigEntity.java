/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.config.entity;

import lombok.Data;

/**
 * Config entity to save the data
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6.1
 */
@Data
public class ConfigEntity {

	/**
	 * Host of the server from the Open-Master
	 */
	private String masterHost = "127.0.0.1";

	/**
	 * Port of the server from the Open-Master
	 */
	private int masterPort = 1024;

	/**
	 * Port of the server of this Open-Container
	 */
	private int port = 1024;

	/**
	 * Token to verify
	 */
	private String token = "";

}
