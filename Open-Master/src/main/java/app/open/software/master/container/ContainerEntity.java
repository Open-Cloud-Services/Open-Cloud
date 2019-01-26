/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.container;

import lombok.*;

/**
 * Entity for an Open-Container object
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.3
 */
@Getter
@RequiredArgsConstructor
public class ContainerEntity {

	/**
	 * {@link ContainerMeta} to get the saved information about the container
	 */
	private final ContainerMeta containerMeta;

	/**
	 * Container is verified in the network system
	 */
	@Setter
	private boolean verified = false;

}