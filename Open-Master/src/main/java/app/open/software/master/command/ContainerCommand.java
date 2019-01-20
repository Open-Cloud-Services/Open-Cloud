/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.command;

import app.open.software.core.command.Command;
import app.open.software.core.command.CommandHelper;
import app.open.software.core.logger.Logger;
import app.open.software.core.service.ServiceCluster;
import app.open.software.master.container.*;
import java.util.UUID;

@Command.Info(names = {"container", "con"}, description = "Configure containers")
public class ContainerCommand implements Command {

	public boolean execute(final String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				Logger.info("<-- Container -->");
				ServiceCluster.get(ContainerEntityService.class).getContainerEntities().forEach(containerEntity -> Logger.info(containerEntity.toString()));
				Logger.info("");
				return true;
			}
			return false;
		} else if (args.length == 2) {
			final String host = args[1];

			switch (args[0]) {
				case "info":
					final ContainerEntity containerEntity = ServiceCluster.get(ContainerEntityService.class).getContainerByHost(host);
					if (containerEntity == null) {
						Logger.warn("Container does not exists!");
					} else {
						Logger.info(containerEntity.toString());
						return true;
					}
					break;

				case "create":
					if (ServiceCluster.get(ContainerEntityService.class).getContainerByHost(host) != null) {
						Logger.warn("Container already exists!");
						return true;
					}
					final String key = ContainerEntityService.generateKey();
					final ContainerEntity newContainer = new ContainerEntity(new ContainerMeta(UUID.randomUUID(), host, key));

					ServiceCluster.get(ContainerEntityService.class).addContainer(newContainer);

					Logger.info("Added new container @" + host);
					Logger.info("Key for the container -> " + key);
					return true;

				case "delete":
					final ContainerEntity oldContainer = ServiceCluster.get(ContainerEntityService.class).getContainerByHost(host);
					if (oldContainer == null) {
						Logger.warn("Container is not created yet!");
						return true;
					}

					ServiceCluster.get(ContainerEntityService.class).removeContainer(oldContainer);
					Logger.info("Removed container @" + host);
					return true;

					default:
						return false;
			}
		}
		return false;
	}

	public CommandHelper helper() {
		return new CommandHelper(this.getInfo()).addToHepList(
						"container list",
						"container info <host>",
						"container create <host>",
						"container delete <host>");
	}

}
