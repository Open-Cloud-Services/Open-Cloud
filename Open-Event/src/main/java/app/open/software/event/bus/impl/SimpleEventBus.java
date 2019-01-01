/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.event.bus.impl;

import app.open.software.core.logger.Logger;
import app.open.software.event.Event;
import app.open.software.event.EventMethodData;
import app.open.software.event.bus.EventBus;
import app.open.software.event.subscribe.Subscribe;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.5
 */
public class SimpleEventBus implements EventBus {

	/**
	 * {@link Map} of all registered {@link Event}s
	 */
	final Map<Class<? extends Event>, List<EventMethodData>> registeredEvents = new HashMap<>();

	/**
	 * {@inheritDoc}
	 */
	public void register(final Object... listener) {
		Arrays.stream(listener).forEach(this::registerListener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void unregister(final Object... listener) {
		Arrays.stream(listener).forEach(this::unregister);
	}

	/**
	 * {@inheritDoc}
	 */
	public void fire(final Event... events) {
		Arrays.stream(events).forEach(this::fire);
	}

	/**
	 * Register an specific listener
	 *
	 * @param listener Listener to register
	 */
	private void registerListener(final Object listener) {
		Arrays.stream(listener.getClass().getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(Subscribe.class))
				.filter(method -> method.getParameterCount() == 1)
				.filter(method -> Event.class.isAssignableFrom(method.getParameterTypes()[0]))
				.forEach(method -> this.registerMethod(method, listener));
	}

	/**
	 * Register a specific {@link Method}
	 *
	 * @param method {@link Method} to register
	 * @param listener Listener where the {@link Method} is located
	 */
	private void registerMethod(final Method method, final Object listener) {
		final EventMethodData eventMethodData = new EventMethodData(listener, method);

		final Class<? extends Event> eventClass = method.getParameterTypes()[0].asSubclass(Event.class);
		if (this.registeredEvents.containsKey(eventClass)) {
			this.registeredEvents.get(eventClass).add(eventMethodData);
		} else {
			this.registeredEvents.put(eventClass, new CopyOnWriteArrayList<>(Arrays.asList(eventMethodData)));
		}
	}

	/**
	 * Unregister an specific listener
	 *
	 * @param listener Listener to unregister
	 */
	private void unregister(final Object listener) {
		this.registeredEvents.values().forEach(eventMethodDataList -> eventMethodDataList
				.stream()
				.filter(eventMethodData -> eventMethodData.getSource().equals(listener))
				.forEach(eventMethodDataList::remove));
	}

	/**
	 * Fire an specific {@link Event}
	 *
	 * @param event {@link Event} to fire
	 */
	private void fire(final Event event) {
		this.registeredEvents.get(event.getClass())
				.stream()
				.sorted(Comparator.comparingInt(value -> value.getMethod().getAnnotation(Subscribe.class).value().getPriority()))
				.forEach(eventMethodData -> {
					final Method method = eventMethodData.getMethod();
					method.setAccessible(true);
					try {
						method.invoke(eventMethodData.getSource(), event);
					} catch (IllegalAccessException | InvocationTargetException e) {
						Logger.error("Can not invoke event method (" + event.getClass().getSimpleName() + ")", e);
					}
		});
	}

}
