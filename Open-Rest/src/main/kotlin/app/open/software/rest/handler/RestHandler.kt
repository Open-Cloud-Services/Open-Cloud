/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.rest.handler

import io.ktor.routing.Route

/**
 * Handler interface to add different routes to the Rest API
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6
 */
interface RestHandler {

    /**
     * Function to add routes
     */
    fun route(): Route.() -> Unit

}