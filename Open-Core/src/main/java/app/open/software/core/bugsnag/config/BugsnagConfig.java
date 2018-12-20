/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.bugsnag.config;

import app.open.software.core.config.DocumentFile;
import com.bugsnag.Bugsnag;
import java.io.*;
import java.nio.file.Files;
import lombok.Getter;

/**
 * Starts and configures {@link Bugsnag}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class BugsnagConfig extends DocumentFile {

	/**
	 * Config entity
	 */
	@Getter
	private BugsnagConfigEntity entity;

	public BugsnagConfig() {
		super("bugsnag");
	}

	/**
	 * {@inheritDoc}
	 */
	public void save() throws IOException {
		if (this.entity == null) {
			this.entity = new BugsnagConfigEntity();
		}

		try (final BufferedWriter writer = Files.newBufferedWriter(this.path)) {
			writer.write(GSON.toJson(this.entity));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void load() throws IOException {
		try (final BufferedReader reader = Files.newBufferedReader(this.path)){
			this.entity = GSON.fromJson(reader, BugsnagConfigEntity.class);
		}
	}

}
