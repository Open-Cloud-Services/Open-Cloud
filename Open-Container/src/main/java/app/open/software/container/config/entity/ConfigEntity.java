/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.config.entity;

import lombok.Data;

@Data
public class ConfigEntity {

	private String host = "127.0.0.1";

	private int port = 1024;

	private String key = "";

}
