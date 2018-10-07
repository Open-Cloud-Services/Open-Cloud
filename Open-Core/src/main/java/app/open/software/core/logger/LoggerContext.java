package app.open.software.core.logger;

import java.text.SimpleDateFormat;
import lombok.Value;

/**
 * Inject into the {@link Logger} for information about the actual {@link Logger} context
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
@Value
public class LoggerContext {

	/**
	 * Prefix for the {@link Logger}
	 */
	private final String prefix;

	/**
	 * Highest level of showing output
	 */
	private final LogLevel level;

	/**
	 * {@link SimpleDateFormat} to format the current date in the console output log
	 */
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

}
