/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.logger.component;

import app.open.software.core.logger.Logger;

public interface LoggerComponent {

	void print();

	default void onFinish() {
		Logger.checkQueue();
	}

}
