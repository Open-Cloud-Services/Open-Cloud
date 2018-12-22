/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.bootstrap;

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
	 */
	public static void main(final String[] args) {
		new MasterBootstrap(args);
	}

	/**
	 * Configure the console arguments and create a new instance of {@link Master}
	 *
	 * @param args Console arguments
	 */
	private MasterBootstrap(final String[] args) {
		final var time = System.currentTimeMillis();

		final var parser = new OptionParser();
		parser.allowsUnrecognizedOptions();
		this.setAcceptedArguments(parser);

		final var set = parser.parse(args);

		new Master().start(set, time);
	}

	/**
	 * Set for the {@link OptionParser} all valid arguments
	 *
	 * @param parser {@link OptionParser} for accepting
	 */
	private void setAcceptedArguments(final OptionParser parser) {
		parser.accepts("help");
		parser.accepts("version");
		parser.accepts("debug");
		parser.accepts("time");
	}

}
