package app.open.software.container.config;

import app.open.software.container.Container;
import app.open.software.container.config.entity.ConfigEntity;
import app.open.software.core.config.DocumentFile;
import java.io.*;
import java.nio.file.Files;

public class ContainerConfig extends DocumentFile {

	public ContainerConfig() {
		super("config");
	}

	public void save() throws IOException {
		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(this.path)) {
			bufferedWriter.write(GSON.toJson(Container.getContainer().getConfigEntity()));
		}
	}

	public void load() throws IOException {
		try (final BufferedReader bufferedReader = Files.newBufferedReader(this.path)) {
			Container.getContainer().setConfigEntity(GSON.fromJson(bufferedReader, ConfigEntity.class));
		}
	}

}
