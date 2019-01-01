/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.config;

import app.open.software.core.config.DocumentFile;
import app.open.software.core.service.ServiceCluster;
import app.open.software.master.container.ContainerEntityService;
import app.open.software.master.container.ContainerMeta;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

public class ContainerConfig extends DocumentFile {

	public ContainerConfig() {
		super("container");
	}

	public void save() throws IOException {
		try (final BufferedWriter bufferedWriter = Files.newBufferedWriter(this.path)){
			bufferedWriter.write(GSON.toJson(ServiceCluster.get(ContainerEntityService.class).getContainerMetas()));
		}
	}

	public void load() throws IOException {
		try (final BufferedReader bufferedReader = Files.newBufferedReader(this.path)){
			ServiceCluster.get(ContainerEntityService.class).createContainerEntitiesFromContainerMetas(GSON.fromJson(bufferedReader, new TypeToken<ArrayList<ContainerMeta>>() {}.getType()));
		}
	}

}
