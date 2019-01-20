/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.event.priority;

import app.open.software.event.Event;
import app.open.software.event.bus.EventBus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Set the priority of an {@link Event}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.5
 */
@Getter
@RequiredArgsConstructor
public enum EventPriority {

	LOWEST(4),
	LOW(3),
	NORMAL(2),
	HIGHER(1),
	HIGHEST(0);

	/**
	 * Int to sort the priority in the {@link EventBus}
	 */
	private final int priority;

}
