/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.core.bugsnag;

import app.open.software.core.bugsnag.config.BugsnagConfig;
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
	 * Module information for bugsnag reports
	 */
	private final String module;

	/**
	 * Version information for bugsnag reports
	 */
	private final String version;

	/**
	 * Bootstrapping {@link Bugsnag}
	 */
	public BugsnagBootstrap(final String module, final String version) {
		this.module = module;
		this.version = version;

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
		bugsnag.setAppVersion(this.version);
		bugsnag.addCallback(report -> {
			if (this.version.equals("Dev-Version")) {
				report.cancel();
			}
			report.setAppInfo("Module", this.module);
		});
	}

}
