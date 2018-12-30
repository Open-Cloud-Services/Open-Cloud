/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.command;

import app.open.software.core.command.Command;
import app.open.software.core.command.CommandHelper;
import app.open.software.core.command.help.CommandHelpAction;

/**
 * Help command to get information about commands
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.2
 */
@Command.Info(names = {"help"}, description = "Get help for all commands")
public class HelpCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	public boolean execute(final String[] args) {
		return new CommandHelpAction().executeCommandHelp(args);
	}

	/**
	 * {@inheritDoc}
	 */
	public CommandHelper helper() {
		return new CommandHelper(this.getInfo()).addToHepList("help <command>");
	}

}
