/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.event.bus;

import app.open.software.event.Event;

/**
 * {@link EventBus} interface to have the possibility to implement different {@link EventBus}es
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.5
 */
public interface EventBus {

	/**
	 * Register listeners
	 *
	 * @param listeners Listeners to register
	 */
	void register(final Object... listeners);

	/**
	 * Unregister listeners
	 *
	 * @param listeners Listeners to unregister
	 */
	void unregister(final Object... listeners);

	/**
	 * Fire {@link Event}s
	 *
	 * @param events {@link Event}s to fire
	 */
	void fire(final Event... events);

}
