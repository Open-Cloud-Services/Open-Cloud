/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.command;

import app.open.software.core.command.Command;
import app.open.software.core.command.CommandHelper;
import app.open.software.core.logger.Logger;

/**
 * Implementation of the {@link Command} interface to delete the log files
 *
 * @author Tammo0987
 * @version 1.1
 * @since 0.1
 */
@Command.Info(names = "log", description = "Deletes the log files")
public class LogDeleteCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	public boolean execute(final String[] args) {
		if (args.length == 1 && args[0].equals("delete")) {
			Logger.getFileHandler().deleteLogFiles();
			return true;
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public CommandHelper helper() {
		return new CommandHelper(this.getInfo()).addToHepList("log delete");
	}

}
