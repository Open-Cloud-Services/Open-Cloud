package app.open.software.core.config;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentFileProviderService implements Service {

	private final List<DocumentFile> files = new ArrayList<>();

	public void init() {
		this.loadFiles();

		Runtime.getRuntime().addShutdownHook(new Thread(this::saveFiles));
	}

	public DocumentFileProviderService addFile(final DocumentFile file) {
		this.files.add(file);
		return this;
	}

	private void loadFiles() {
		this.files.forEach(file -> {
			try {
				file.load();
			} catch (IOException e) {
				Logger.error("Could not load file", e);
			}
		});
	}

	private void saveFiles() {
		this.files.forEach(file -> {
			try {
				file.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
