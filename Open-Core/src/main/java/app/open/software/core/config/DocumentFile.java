/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.config;

import app.open.software.core.logger.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.*;

/**
 * Represents a config file
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.2
 */
public abstract class DocumentFile {

	/**
	 * {@link Gson} constant to serialize and deserialize with json
	 */
	protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * {@link Path} of this {@link DocumentFile}
	 */
	protected final Path path;

	/**
	 * Create a new {@link DocumentFile}
	 *
	 * @param name Name of the file
	 */
	public DocumentFile(final String name) {
		this.path = Paths.get("config/" + name + ".json");

		if (Files.notExists(this.path.getParent())) {
			try {
				Files.createDirectories(this.path.getParent());
			} catch (IOException e) {
				Logger.error("Could not create config directory", e);
			}
		}

		if (Files.notExists(this.path)) {
			try {
				this.save();
			} catch (IOException e) {
				Logger.error("Could not create " + name + ".json", e);
			}
		}
	}

	/**
	 * Save the file
	 *
	 * @throws IOException An I/O error occurs
	 */
	public abstract void save() throws IOException;

	/**
	 * Load the file
	 *
	 * @throws IOException An I/O error occurs
	 */
	public abstract void load() throws IOException;

}
