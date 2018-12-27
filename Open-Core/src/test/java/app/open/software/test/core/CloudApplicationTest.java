/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.test.core;

import app.open.software.core.CloudApplication;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class CloudApplicationTest {

	private final CloudApplication application = mock(CloudApplication.class);

	private final String module = "Open-Test";

	@Before
	public void setUpMockObject() {
		doNothing().when(this.application).start(null, 0);
		doNothing().when(this.application).printStartHeader(module);
		doNothing().when(this.application).shutdown();

		assertNotNull(this.application);
	}

	@Test
	public void testStarting() {
		this.application.start(null, 0);
		verify(this.application, times(1)).start(null, 0);
	}

	@Test
	public void testHeadPrinting() {
		this.application.printStartHeader(this.module);
		verify(this.application, times(1)).printStartHeader(this.module);
	}

	@Test
	public void testShutdown() {
		this.application.shutdown();
		verify(this.application, times(1)).shutdown();
	}

}
