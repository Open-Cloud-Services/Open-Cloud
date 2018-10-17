/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.command;

import app.open.software.core.logger.Logger;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Helper class to print the syntax of the {@link Command}s
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
@RequiredArgsConstructor
public class CommandHelper {

	/**
	 * {@link Command} main name
	 */
	@Getter
	private final String command;

	/**
	 * List of sub commands
	 */
	private List<String> helpList = new ArrayList<>();

	/**
	 * Add sub commands to the list
	 *
	 * @param help Array of the sub commmands
	 */
	public void addToHelpList(final String... help) {
		this.helpList.addAll(Arrays.asList(help));
	}

	/**
	 * Print the help in the console
	 */
	void printHelp() {
		Logger.info("<-- Command Help -->");
		Logger.info(this.command);
		this.helpList.forEach(Logger::info);
		Logger.info("");
	}

}
