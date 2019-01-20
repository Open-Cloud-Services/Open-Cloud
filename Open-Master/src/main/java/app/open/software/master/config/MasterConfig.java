/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.config;

import app.open.software.core.config.DocumentFile;
import app.open.software.master.Master;
import app.open.software.master.config.entity.ConfigEntity;
import java.io.*;
import java.nio.file.Files;

public class MasterConfig extends DocumentFile {

	public MasterConfig() {
		super("config");
	}

	public void save() throws IOException {
		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(this.path)) {
			bufferedWriter.write(GSON.toJson(Master.getMaster().getConfigEntity()));
		}
	}

	public void load() throws IOException {
		try (final BufferedReader bufferedReader = Files.newBufferedReader(this.path)) {
			Master.getMaster().setConfigEntity(GSON.fromJson(bufferedReader, ConfigEntity.class));
		}
	}

}
