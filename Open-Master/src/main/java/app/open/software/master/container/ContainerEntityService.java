/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.master.container;

import app.open.software.core.service.Service;
import java.util.*;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Handle all containers
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6.1
 */
public class ContainerEntityService implements Service {

	/**
	 * List of all {@link ContainerEntity}s
	 */
	@Getter
	private final List<ContainerEntity> containerEntities = new ArrayList<>();

	/**
	 * @return Random generated token
	 */
	public static String generateToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop() {
		//TODO send master terminate
	}

	/**
	 * Add a {@link ContainerEntity}
	 *
	 * @param containerEntity {@link ContainerEntity} to add
	 */
	public void addContainer(final ContainerEntity containerEntity) {
		this.containerEntities.add(containerEntity);
	}

	/**
	 * @return {@link ContainerEntity} by host
	 *
	 * @param host Host address
	 */
	public final ContainerEntity getContainerByHost(final String host) {
		return this.containerEntities.stream().filter(containerEntity -> containerEntity.getContainerMeta().getHost().equals(host)).findFirst().orElse(null);
	}

	/**
	 * Remove {@link ContainerEntity}
	 *
	 * @param containerEntity {@link ContainerEntity} to remove
	 */
	public void removeContainer(final ContainerEntity containerEntity) {
		this.containerEntities.remove(containerEntity);
	}

	/**
	 * Create {@link ContainerEntity}s from {@link ContainerMeta}s
	 *
	 * @param containerMetas {@link ArrayList} of {@link ContainerMeta}s
	 */
	public void createContainerEntitiesFromContainerMetas(final ArrayList<ContainerMeta> containerMetas) {
		this.containerEntities.addAll(containerMetas.stream().map(ContainerEntity::new).collect(Collectors.toList()));
	}

	/**
	 * @return {@link List} of {@link ContainerMeta}s
	 */
	public final ArrayList<ContainerMeta> getContainerMetas() {
		return (ArrayList<ContainerMeta>) this.containerEntities.stream().map(ContainerEntity::getContainerMeta).collect(Collectors.toList());
	}

}
