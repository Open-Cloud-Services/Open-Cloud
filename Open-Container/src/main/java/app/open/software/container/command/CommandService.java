/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.container.command;

import app.open.software.container.service.Service;
import com.google.common.reflect.ClassPath;
import java.io.IOException;
import java.util.*;

/**
 *
 *
 * @author Tammo0987, x7Airworker
 */
public class CommandService implements Service {

	private final Map<String, Command> commands = new HashMap<>();

	public void init() {
		try {
			ClassPath.from(this.getClass().getClassLoader()).getTopLevelClassesRecursive("app.open.software")
					.stream()
					.map(ClassPath.ClassInfo::load)
					.filter(Command.class::isAssignableFrom)
					.filter(aClass -> aClass.isAnnotationPresent(Command.Info.class))
					.forEach(aClass -> {
						try {
							final var command = (Command) aClass.newInstance();
							Arrays.stream(command.getInfo().names()).forEach(name -> this.commands.put(name, command));
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void executeCommand(final String message) {
		final var arguments = message.split("\\s+");

		final var command = this.commands.get(arguments[0]);
		if(command == null) return;

		final var newArgs = new String[arguments.length - 1];
		System.arraycopy(arguments, 1, newArgs, 0, newArgs.length);

		if (!command.execute(newArgs) && command.helper() != null) {
			command.helper().printHelp();
		}
	}
}
