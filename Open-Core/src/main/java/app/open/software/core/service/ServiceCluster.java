/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.service;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class to provide services to remove many instances from the main classes, to decrease the size to improve the
 * overview
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class ServiceCluster {

	/**
	 * List which is holding all the instance of {@link Service}s
	 */
	private static final ArrayList<Service> serviceList = new ArrayList<>();

	/**
	 * Add as many {@link Service}s as you want to the {@link ServiceCluster#serviceList}
	 *
	 * @param services {@link Service}s you want to add
	 */
	public static void addServices(final Service... services) {
		serviceList.addAll(Arrays.asList(services));
	}

	/**
	 * Initialise all {@link Service}s
	 */
	public static void init() {
		serviceList.forEach(Service::init);
	}

	/**
	 * Stop all {@link Service}s
	 */
	public static void stop() {
		serviceList.forEach(Service::stop);
	}

	/**
	 * @return The service instance filtered by {@code type}
	 *
	 * @param type Class of the {@link Service}, which ist requested
	 * @param <T> Type of the {@link Service} to cast it automatically to the wanted {@link Service}
	 */
	public static <T extends Service> T get(final Class<T> type) {
		return type.cast(serviceList.stream().filter(service -> service.getClass() == type).findFirst().orElse(null));
	}

}
