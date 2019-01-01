/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.event.service;

import app.open.software.core.service.Service;
import app.open.software.event.bus.EventBus;
import app.open.software.event.bus.impl.SimpleEventBus;
import lombok.Getter;

/**
 * Handle events in a {@link Service}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.5
 */
@Getter
public class EventService implements Service {

	/**
	 * {@link EventBus} instance
	 */
	private final EventBus eventBus = new SimpleEventBus();

	/**
	 * Register a listener
	 *
	 * @param listener Listener to register
	 */
	public void registerListener(final Object...listener) {
		this.eventBus.register(listener);
	}

	/**
	 * Unregister a listener
	 *
	 * @param listener Listener to unregister
	 */
	public void unregisterListener(final Object... listener) {
		this.eventBus.unregister(listener);
	}

}
