/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.rest.handler;

/**
 * Rest handler interface to implement different rest api handler
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6
 */
public interface RestHandler {

	/**
	 * Register all the routes from this handler
	 */
	void route();

}
