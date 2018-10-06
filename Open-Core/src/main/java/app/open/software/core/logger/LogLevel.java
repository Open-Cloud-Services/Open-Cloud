package app.open.software.core.logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum LogLevel {

	DEBUG(0),
	INFO(1),
	WARNING(2),
	ERROR(3);

	@Getter
	private final int level;

	public final String getName() {
		return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
	}
}
