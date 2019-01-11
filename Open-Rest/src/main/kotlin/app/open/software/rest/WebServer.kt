package app.open.software.rest

import app.open.software.rest.version.RestVersion
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit

/**
 * WebServer to start the Rest API
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.6
 */
class WebServer(private val port: Int, private val versionList: List<RestVersion>) {

    /**
     * WebServer var
     */
    private var server: NettyApplicationEngine? = null

    /**
     * Init the WebServer
     */
    init {
        this.server = embeddedServer(Netty, this.port) {
            install(DefaultHeaders)
            install(Compression)

            install(ContentNegotiation) {
                gson {
                    excludeFieldsWithModifiers(Modifier.TRANSIENT)
                }
            }

            routing {
                versionList.forEach {
                    it.setupRoutes().invoke(this)
                }
            }
        }
    }

    /**
     * Start the WebServer
     */
    fun start() {
        this.server?.start(true)
    }

    /**
     * Stop the WebServer
     */
    fun stop() {
        this.server?.stop(5, 5, TimeUnit.SECONDS)
    }

}