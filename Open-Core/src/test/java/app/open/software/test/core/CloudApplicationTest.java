package app.open.software.test.core;

import app.open.software.core.CloudApplication;
import org.junit.*;
import static org.junit.Assert.assertNotNull;
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
