package app.open.software.master.command;

import app.open.software.core.command.Command;
import app.open.software.core.command.CommandHelper;
import app.open.software.core.logger.Logger;

@Command.Info(names = "log", description = "Deletes the log files")
public class LogDeleteCommand implements Command {

	/**
	 * {@inheritDoc}
	 */
	public boolean execute(final String[] args) {
		if (args.length == 1 && args[0].equals("delete")) {
			Logger.info("Deleting log files...");
			Logger.getFileHandler().deleteLogFiles();
			Logger.info("Done!");
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public CommandHelper helper() {
		return new CommandHelper("log").addToHepList("log delete");
	}

}
