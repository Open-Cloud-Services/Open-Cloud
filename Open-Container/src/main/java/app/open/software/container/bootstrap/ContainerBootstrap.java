package app.open.software.container.bootstrap;

import app.open.software.container.Container;
import app.open.software.core.exception.JavaVersionRequiredException;
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
	 *
	 * @throws JavaVersionRequiredException Required Java version is not installed
	 */
	public static void main(final String[] args) throws JavaVersionRequiredException {
		if (Double.parseDouble(System.getProperty("java.class.version")) < 55) {
			throw new JavaVersionRequiredException();
		} else {
			new ContainerBootstrap(args);
		}
	}

	/**
	 * Configure the console arguments and create a new instance of {@link Container}
	 *
	 * @param args Console arguments
	 */
	private ContainerBootstrap(final String[] args) {
		final var parser = new OptionParser();
		this.acceptArguments(parser);

		final var set = parser.parse(args);
		new Container();
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
	}

}
