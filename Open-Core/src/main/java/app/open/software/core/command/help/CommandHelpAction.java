/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.command.help;

import app.open.software.core.command.Command;
import app.open.software.core.command.CommandService;
import app.open.software.core.logger.Logger;
import app.open.software.core.service.ServiceCluster;
import java.util.LinkedHashSet;

/**
 * Execute the help command
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.2
 */
public class CommandHelpAction {

	/**
	 * Process the helping command
	 *
	 * @param args {@link Command} arguments
	 *
	 * @return Success of the action
	 */
	public boolean executeCommandHelp(final String[] args) {
		if (args.length == 1) {
			final var command = ServiceCluster.get(CommandService.class).getCommands().get(args[0]);
			if (command == null) {
				Logger.info("Unknown command! Type help for help...");
			}

			if (command.helper() != null) {
				command.helper().printHelp();
			}
		} else {
			final var set = new LinkedHashSet<Command>();
			ServiceCluster.get(CommandService.class).getCommands().forEach((key, command) -> set.add(command));

			Logger.info("<-- Command Help -->");
			Logger.info("");
			set.forEach(command -> Logger.info(command.getInfo().names()[0] + " -> " + command.getInfo().description()));
			Logger.info("");
		}

		return true;
	}

}
