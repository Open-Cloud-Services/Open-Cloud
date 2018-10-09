/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.command;

import app.open.software.core.logger.Logger;
import java.util.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandHelper {

	@Getter
	private final String command;

	private List<String> helpList = new ArrayList<>();

	public void addToHelpList(final String... help) {
		this.helpList.addAll(Arrays.asList(help));
	}

	public void printHelp() {
		Logger.info("<-- Command Help -->");
		Logger.info(this.command);
		this.helpList.forEach(Logger::info);
		Logger.info("");
	}

}
