/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.exception;

/**
 * This {@link Exception} should be thrown, if the required Java version is not installed on the current machine
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class JavaVersionRequiredException extends Exception{

	/**
	 * Create a new {@link JavaVersionRequiredException} with the default message
	 */
	public JavaVersionRequiredException() {
		super("Java 11 is required to start the Open-Cloud!");
	}

}
