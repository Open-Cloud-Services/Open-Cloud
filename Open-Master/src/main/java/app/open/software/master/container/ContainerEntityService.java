/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.container;

import app.open.software.core.logger.Logger;
import app.open.software.core.service.Service;
import io.netty.channel.Channel;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;

public class ContainerEntityService implements Service {

	@Getter
	private final List<ContainerEntity> containerEntities = new ArrayList<>();

	public static String generateKey() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public void stop() {
		this.containerEntities.forEach(containerEntity -> {
			try {
				containerEntity.disconnect();
			} catch (InterruptedException e) {
				Logger.error("Interrupted while disconnecting", e);
			}
		});
	}

	public void addContainer(final ContainerEntity containerEntity) {
		this.containerEntities.add(containerEntity);
	}

	public final ContainerEntity getContainerByHost(final String host) {
		return this.containerEntities.stream().filter(containerEntity -> containerEntity.getContainerMeta().getHost().equals(host)).findFirst().orElse(null);
	}

	public final ContainerEntity getContainerByChannel(final Channel channel) {
		return this.containerEntities.stream().filter(containerEntity -> containerEntity.getContainerMeta().getHost().equals(this.getHostByChannel(channel))).findFirst().orElse(null);
	}

	public void removeContainer(final ContainerEntity containerEntity) {
		this.containerEntities.remove(containerEntity);
	}

	public void createContainerEntitiesFromContainerMetas(final ArrayList<ContainerMeta> containerMetas) {
		this.containerEntities.addAll(containerMetas.stream().map(ContainerEntity::new).collect(Collectors.toList()));
	}

	public final ArrayList<ContainerMeta> getContainerMetas() {
		return (ArrayList<ContainerMeta>) this.containerEntities.stream().map(ContainerEntity::getContainerMeta).collect(Collectors.toList());
	}

	public String getHostByChannel(final Channel channel) {
		return ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostName();
	}

}
