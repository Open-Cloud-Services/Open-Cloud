/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.command;

import app.open.software.container.Container;
import app.open.software.core.command.Command;
import app.open.software.core.command.CommandHelper;

/**
 * Implementation of the {@link Command} interface to stop the Open-Container
 *
 * @author Tammo0987
 * @version 1.1
 * @since 0.1
 */
@Command.Info(names = {"stop", "shutdown", "exit"}, description = "Stops the Open-Container")
public class StopCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	public boolean execute(final String[] args) {
		Container.getContainer().shutdown();
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public CommandHelper helper() {
		return new CommandHelper(this.getInfo());
	}

}
