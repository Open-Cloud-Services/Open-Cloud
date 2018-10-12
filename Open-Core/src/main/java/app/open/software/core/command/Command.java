/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.command;

import java.lang.annotation.*;

/**
 * Interface to implement new {@link Command}s for the command system to control the system
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public interface Command {

	/**
	 * Execute the command
	 *
	 * @param args The inputted arguments
	 *
	 * @return If the command was right executed
	 */
	boolean execute(final String[] args);

	/**
	 * @return {@link CommandHelper} to get the syntax for the {@link Command}
	 */
	CommandHelper helper();

	/**
	 * @return {@link Command.Info} to get more information about the{@link Command}
	 */
	default Info getInfo() {
		return this.getClass().getAnnotation(Info.class);
	}

	/**
	 * Helper Annotation to get more information about the {@link Command}
	 *
	 * @author Tammo0987
	 * @version 1.0
	 * @since 0.1
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface Info {

		/**
		 * @return Name and aliases of the {@link Command}
		 */
		String[] names();

		/**
		 * @return Description of the {@link Command}
		 */
		String description();
	}


}
