/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.rest.version

import app.open.software.rest.handler.RestHandler
import io.ktor.routing.Routing
import io.ktor.routing.route

class RestVersion(var version: Int?, var restHandler: List<RestHandler>) {

    fun setupRoutes(): Routing.() -> Unit {
        return {
            route("/api/v" + this@RestVersion.version.toString()) {
                restHandler.forEach {
                    it.route().invoke(this)
                }
            }
        }
    }

}