/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.event;

import java.lang.reflect.Method;
import lombok.Value;

/**
 * Object for invoking {@link Method}s in listeners
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.5
 */
@Value
public class EventMethodData {

	/**
	 * Instance of the listener
	 */
	private final Object source;

	/**
	 * Event {@link Method} to invoke later
	 */
	private final Method method;

}
