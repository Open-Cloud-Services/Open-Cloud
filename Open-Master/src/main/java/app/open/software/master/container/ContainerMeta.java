/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.container;

import java.util.UUID;
import lombok.Data;

@Data
public class ContainerMeta {

	private final UUID uuid;

	private final String host;

	private final String key;

}
