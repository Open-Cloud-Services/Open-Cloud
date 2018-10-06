package app.open.software.core.logger.component;

import app.open.software.core.logger.Logger;

public interface LoggerComponent {

	void print();

	default void onFinish() {
		Logger.checkQueue();
	}

	void logToFile();

}
