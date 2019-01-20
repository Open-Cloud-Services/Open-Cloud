/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.setup;

import app.open.software.container.Container;
import app.open.software.core.logger.Logger;
import app.open.software.core.setup.request.impl.StringRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Setup to configure the setup of the Open-Container
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public class ContainerSetup {

	/**
	 * {@inheritDoc}
	 */
	public void setup(final BufferedReader reader) throws IOException {
		if (!Container.getContainer().getConfigEntity().getKey().isEmpty()) {
			return;
		} else {
			Logger.info("Welcome to the setup!");
			new StringRequest("Type in the key from the Open-Master", reader).request(key -> Container.getContainer().getConfigEntity().setKey(key));
		}

		Logger.info("Setup completed!");
	}

}
