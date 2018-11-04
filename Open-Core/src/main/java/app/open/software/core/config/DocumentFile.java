package app.open.software.core.config;

import app.open.software.core.logger.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.nio.file.*;

public abstract class DocumentFile {

	protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	protected final Path path;

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

	abstract void save() throws IOException;

	abstract void load() throws IOException;

}
