/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master;

import app.open.software.core.CloudApplication;
import joptsimple.OptionSet;
import lombok.Getter;

/**
 *
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class Master implements CloudApplication {

	/**
	 * Singleton instance of {@link Master}
	 */
	@Getter
	private static Master master;

	/**
	 * {@inheritDoc}
	 */
	public void start(final OptionSet set) {
		if(master == null) master = this;
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {

	}
}
