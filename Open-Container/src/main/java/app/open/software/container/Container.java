package app.open.software.container;

import app.open.software.core.CloudApplication;
import app.open.software.core.logger.*;
import java.util.HashMap;
import joptsimple.OptionSet;
import lombok.Getter;

/**
 * Open-Container main class to control everything
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class Container implements CloudApplication {

	/**
	 * Singleton instance of {@link Container}
	 */
	@Getter
	private static Container container;

	/**
	 * {@inheritDoc}
	 */
	public void start(final OptionSet set, final long startUpTime) {
		if(container == null) container = this;

		Logger.setContext(new LoggerContext("Open-Container", set.has("debug") ? LogLevel.DEBUG : LogLevel.INFO));

		if (set.has("help")) {
			this.printArgumentHelp();
			return;
		}

		this.printStartHeader("Open-Container");

		if (set.has("startuptime")) {
			Logger.info("Time to start: " + (System.currentTimeMillis() - startUpTime) + " ms");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void shutdown() {

	}

	/**
	 * Print the help for the program arguments if requested
	 */
	private void printArgumentHelp() {
		final var map = new HashMap<String, String>();
		map.put("help", "Print all possible runtime arguments");
		map.put("version", "Print the current version of Open-Cloud");
		map.put("debug", "Enable debug logging");
		map.put("startuptime", "Show after starting the time to start");

		Logger.info("Open-Cloud Help:");
		Logger.info("");
		map.forEach((name, description) -> Logger.info(name + " -> " + description));
		Logger.info("");
	}

}
