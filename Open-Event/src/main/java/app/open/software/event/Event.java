/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.event;

import lombok.Getter;
import lombok.Setter;

/**
 * Event interface to implement various events
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.5
 */
public abstract class Event {

	/**
	 * Check if the event was cancelled
	 */
	@Setter
	@Getter
	private boolean cancelled = false;

}
