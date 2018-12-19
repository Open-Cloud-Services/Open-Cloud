/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.test.core.service;

import app.open.software.core.service.Service;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ServiceTest {

	private final Service service = mock(Service.class);

	@Before
	public void setUpMockObject() {
		doNothing().when(this.service).init();
	}

	@Test
	public void testService() {
		assertNotNull(this.service);

		this.service.init();
		verify(this.service, times(1)).init();
	}

}
