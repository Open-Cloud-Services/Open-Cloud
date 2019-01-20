/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.bootstrap;

import app.open.software.container.Container;
import joptsimple.OptionParser;

/**
 * Main class to bootstrap the Open-Container module and parse the console arguments
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class ContainerBootstrap {

	/**
	 * Main method to start the Open-Container module
	 *
	 * @param args Console arguments
	 */
	public static void main(final String[] args) {
		new ContainerBootstrap(args);
	}

	/**
	 * Configure the console arguments and create a new instance of {@link Container}
	 *
	 * @param args Console arguments
	 */
	private ContainerBootstrap(final String[] args) {
		final var time = System.currentTimeMillis();

		final var parser = new OptionParser();
		parser.allowsUnrecognizedOptions();
		this.setAcceptedArguments(parser);

		final var set = parser.parse(args);

		new Container().start(set, time);
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
		parser.accepts("disable-updater");
	}

}
