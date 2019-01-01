/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.event.subscribe;

import app.open.software.event.Event;
import app.open.software.event.bus.EventBus;
import app.open.software.event.priority.EventPriority;
import java.lang.annotation.*;

/**
 * Subscribe an {@link Event} of the {@link EventBus}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.5
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribe {

	/**
	 * @return {@link EventPriority} of the {@link Event} method
	 */
	EventPriority value() default EventPriority.NORMAL;

}
