/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.thread;

/**
 * {@link Thread}Builder to easily create and configure {@link Thread}s
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class ThreadBuilder {

	/**
	 * {@link Thread} to configure
	 */
	private final Thread thread;

	public ThreadBuilder(final String name, final Runnable runnable) {
		this.thread = new Thread(runnable, name);
	}

	/**
	 * @return Configured {@link ThreadBuilder} with a daemon {@link Thread}
	 */
	public ThreadBuilder setDaemon() {
		this.thread.setDaemon(true);
		return this;
	}

	/**
	 * @return The started {@link Thread}
	 */
	public final Thread startAndGet() {
		this.thread.start();
		return this.thread;
	}

	/**
	 * Start the configured {@link Thread}
	 */
	public void start() {
		this.thread.start();
	}

}
