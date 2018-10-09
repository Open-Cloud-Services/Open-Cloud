/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.command;

import java.lang.annotation.*;

public interface Command {

	boolean execute(final String[] args);

	CommandHelper helper();

	default Info getInfo() {
		return this.getClass().getAnnotation(Info.class);
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	@interface Info {

		String[] names();

		String description();
	}


}
