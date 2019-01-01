/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.service;

/**
 * Interface which can be implemented, to add a class to the {@link ServiceCluster} to get them as a {@link Service}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public interface Service {

	/**
	 * Optional initialise method to init the {@link Service}
	 */
	default void init() {}

	/**
	 * Optional stop method to stop the {@link Service}
	 */
	default void stop() {}

}
