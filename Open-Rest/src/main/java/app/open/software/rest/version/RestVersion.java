/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.rest.version;

import app.open.software.rest.handler.RestHandler;
import java.util.*;
import lombok.*;

import static spark.Spark.*;

/**
 * Rest version to register different api versions
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6
 */
@RequiredArgsConstructor
public class RestVersion {

	/**
	 * Current version
	 */
	private final int version;

	/**
	 * {@link List} of all registered {@link RestHandler}
	 */
	private final List<RestHandler> handler = new ArrayList<>();

	/**
	 * Setup all routes
	 */
	public void init() {
		path("/api/v" + this.version, () -> this.handler.forEach(RestHandler::route));
	}

	/**
	 * Register {@link RestHandler}
	 *
	 * @param handlers Varargs of {@link RestHandler} to register
	 *
	 * @return Instance
	 */
	public final RestVersion registerHandlers(final RestHandler... handlers) {
		this.handler.addAll(Arrays.asList(handlers));
		return this;
	}

}
