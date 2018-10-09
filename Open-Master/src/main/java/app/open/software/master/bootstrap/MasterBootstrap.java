/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.bootstrap;

import app.open.software.core.exception.JavaVersionRequiredException;
import app.open.software.master.Master;
import joptsimple.OptionParser;

/**
 * Main class to bootstrap the Open-Master module and parse the console arguments
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class MasterBootstrap {

	/**
	 * Main method to start the Open-Master module
	 *
	 * @param args Console arguments
	 *
	 * @throws JavaVersionRequiredException Required Java version is not installed
	 */
	public static void main(final String[] args) throws JavaVersionRequiredException {
		if (Double.parseDouble(System.getProperty("java.class.version")) < 55) {
			throw new JavaVersionRequiredException();
		} else {
			new MasterBootstrap(args);
		}
	}

	/**
	 * Configure the console arguments and create a new instance of {@link Master}
	 *
	 * @param args Console arguments
	 */
	private MasterBootstrap(final String[] args) {
		final var startUpTime = System.currentTimeMillis();

		final var parser = new OptionParser();
		this.acceptArguments(parser);

		final var set = parser.parse(args);

		new Master().start(set, startUpTime);
	}

	/**
	 * Set for the {@link OptionParser} all valid arguments
	 *
	 * @param parser {@link OptionParser} for accepting
	 */
	private void acceptArguments(final OptionParser parser) {
		parser.accepts("help");
		parser.accepts("version");
		parser.accepts("debug");
		parser.accepts("startuptime");
	}

}
