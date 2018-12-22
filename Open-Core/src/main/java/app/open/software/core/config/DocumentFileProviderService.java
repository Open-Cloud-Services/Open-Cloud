/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.config;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link Service} to handle the config files
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.2
 */
public class DocumentFileProviderService implements Service {

	/**
	 * {@link List} of all {@link DocumentFile}s
	 */
	private final List<DocumentFile> files = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	public void init() {
		this.loadFiles();

		Runtime.getRuntime().addShutdownHook(new Thread(this::saveFiles));
	}

	/**
	 * Add {@link DocumentFile} to handle it with the {@link Service}
	 *
	 * @param file {@link DocumentFile} to add
	 *
	 * @return instance
	 */
	public DocumentFileProviderService addFile(final DocumentFile file) {
		this.files.add(file);
		return this;
	}

	/**
	 * Load all files
	 */
	private void loadFiles() {
		this.files.forEach(file -> {
			try {
				file.load();
			} catch (IOException e) {
				Logger.error("Could not load file", e);
			}
		});
	}

	/**
	 * Save all files
	 */
	private void saveFiles() {
		this.files.forEach(file -> {
			try {
				file.save();
			} catch (IOException e) {
				Logger.error("Could not save file", e);
			}
		});
	}

}
