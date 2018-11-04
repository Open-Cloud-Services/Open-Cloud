/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.command;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.Service;
import app.open.software.core.thread.ThreadBuilder;
import com.google.common.reflect.ClassPath;
import java.io.*;
import java.util.*;

/**
 * {@link Service} to handle all the {@link Command}s
 *
 * @author Tammo0987, x7Airworker
 * @version 1.0
 * @since 0.1
 */
public class CommandService implements Service {

	/**
	 * {@link Map} to hold all the {@link Command} with the name or alias as the key
	 */
	private final Map<String, Command> commands = new HashMap<>();

	/**
	 * Command-{@link Thread} is running
	 */
	private boolean running;

	/**
	 * {@inheritDoc}
	 */
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
							Logger.error("Could not load command", e);
						}
					});
		} catch (IOException e) {
			Logger.error("Could not load commands", e);
		}
	}

	/**
	 * Start the Command-Dispatcher {@link Thread}
	 */
	public void start() {
		this.running = true;

		new ThreadBuilder("Command-Dispatcher", () -> {
			final var reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				String input;
				while (this.running && (input = reader.readLine()) != null) {
					this.dispatchCommand(input);
				}
			} catch (IOException e) {
				Logger.error("Could not read from console", e);
			}
		}).start();
	}

	/**
	 * Stop the Command-Dispatcher {@link Thread}
	 */
	public void stop() {
		this.running = false;
	}

	/**
	 * Execute a command by the console input
	 *
	 * @param message Console input
	 */
	private void dispatchCommand(final String message) {
		final var arguments = message.split("\\s+");

		final var command = this.commands.get(arguments[0]);
		if(command == null){
			Logger.info("Unknown command! Type help for help...");
			return;
		}

		final var newArgs = new String[arguments.length - 1];
		System.arraycopy(arguments, 1, newArgs, 0, newArgs.length);

		if (!command.execute(newArgs) && command.helper() != null) {
			command.helper().printHelp();
		}
	}
}
