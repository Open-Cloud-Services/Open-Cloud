package app.open.software.core.logger;

import java.text.SimpleDateFormat;
import lombok.Value;

@Value
public class LoggerContext {

	private final String prefix;

	private final LogLevel level;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

}
