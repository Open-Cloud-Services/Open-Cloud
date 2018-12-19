package app.open.software.master.bootstrap;

import app.open.software.master.Master;
import app.open.software.master.config.configs.BugsnagConfig;
import com.bugsnag.Bugsnag;
import java.io.IOException;
import lombok.Getter;

/**
 * Starts and configures {@link Bugsnag}
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.1
 */
public class BugsnagBootstrap {

	/**
	 * {@link Bugsnag} object
	 */
	@Getter
	private Bugsnag bugsnag;

	/**
	 * Bootstrapping {@link Bugsnag}
	 */
	public BugsnagBootstrap() {
		final String key = this.loadBugsnagKey(new BugsnagConfig());
		this.bugsnag = new Bugsnag(key);
		this.configureBugsnagReports(this.bugsnag);
	}

	/**
	 * @return {@link Bugsnag} key
	 *
	 * @param bugsnagConfig Config to load the bugsnag key
	 */
	private String loadBugsnagKey(final BugsnagConfig bugsnagConfig) {
		try {
			bugsnagConfig.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bugsnagConfig.getEntity().getBugsnagKey();
	}

	/**
	 * Init instance of {@link Bugsnag} to identify reported errors
	 */
	private void configureBugsnagReports(final Bugsnag bugsnag) {
		final String version = Master.getMaster().getVersion();

		bugsnag.setAppVersion(version);
		bugsnag.addCallback(report -> {
			if (version.equals("Dev-Version")) {
				report.cancel();
			}
			report.setAppInfo("Module", "Open-Master");
		});
	}

}
