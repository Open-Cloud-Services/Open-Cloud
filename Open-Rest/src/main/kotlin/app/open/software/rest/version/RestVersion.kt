/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.rest.version

import app.open.software.rest.handler.RestHandler
import io.ktor.routing.Routing
import io.ktor.routing.route

/**
 * Create different versions of the Rest API
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6
 */
class RestVersion(private val version: Int?, private val restHandler: List<RestHandler>) {

    /**
     * Setup all routes
     */
    fun setupRoutes(): Routing.() -> Unit {
        return {
            route("/api/v" + this@RestVersion.version.toString()) {
                this@RestVersion.restHandler.forEach {
                    it.route().invoke(this)
                }
            }
        }
    }

}