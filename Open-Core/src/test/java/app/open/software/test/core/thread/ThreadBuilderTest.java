/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.test.core.thread;

import app.open.software.core.thread.ThreadBuilder;
import static org.junit.Assert.*;
import org.junit.Test;

public class ThreadBuilderTest {

	@Test
	public void testThreadBuilder() {
		final var thread = new ThreadBuilder("Daemon-Test", () -> {}).setDaemon().startAndGet();

		assertNotNull(thread);
		assertEquals("Daemon-Test", thread.getName());
		assertTrue(thread.isAlive());
		assertTrue(thread.isDaemon());
		assertFalse(thread.isInterrupted());

		thread.interrupt();

		assertTrue(thread.isInterrupted());
	}

}
