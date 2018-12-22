/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.command;

import app.open.software.core.logger.Logger;
import java.util.*;
import lombok.RequiredArgsConstructor;

/**
 * Helper class to print the syntax of the {@link Command}s
 *
 * @author Tammo0987
 * @version 1.1
 * @since 0.1
 */
@RequiredArgsConstructor
public class CommandHelper {

	/**
	 * {@link Command.Info} with information about the command
	 */
	private final Command.Info commandInfo;

	/**
	 * List of sub commands
	 */
	private List<String> helpList = new ArrayList<>();

	/**
	 * Add sub commands to the list
	 *
	 * @param help Array of sub commands
	 *
	 * @return Instance to pass the builder pattern
	 */
	public CommandHelper addToHepList(final String... help) {
		this.helpList.addAll(Arrays.asList(help));
		return this;
	}

	/**
	 * Print the help in the console
	 */
	public void printHelp() {
		Logger.info("<-- Command Help -->");
		Logger.info(this.commandInfo.names()[0] + " -> " + this.commandInfo.description());
		this.helpList.forEach(Logger::info);
		Logger.info("");
	}

}
