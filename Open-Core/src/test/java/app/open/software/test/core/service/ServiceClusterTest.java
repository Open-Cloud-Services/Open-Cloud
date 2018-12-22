/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.test.core.service;

import app.open.software.core.service.Service;
import app.open.software.core.service.ServiceCluster;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class ServiceClusterTest {

	private final Service service = mock(Service.class);

	@Before
	public void setUpMockObject() {
		doNothing().when(this.service).init();
	}

	@Test
	public void testCluster() {
		ServiceCluster.addServices(this.service);
		assertNotNull(ServiceCluster.get(this.service.getClass()));

		ServiceCluster.init();
		verify(this.service, times(1)).init();
	}

}
