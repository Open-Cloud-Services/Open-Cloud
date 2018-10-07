/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.component;

import app.open.software.core.logger.Logger;

/**
 * Interface to implement several type of console and logging output
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public interface LoggerComponent {

	/**
	 * Print the component
	 */
	void print();

	/**
	 * The printing of the component is finished
	 */
	default void onFinish() {
		Logger.checkQueue();
	}

}
