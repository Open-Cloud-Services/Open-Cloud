/*
 * Copyright (c) 2018 - 2019, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.rest.handler

import io.ktor.routing.Route

interface RestHandler {

    fun route(): Route.() -> Unit

}