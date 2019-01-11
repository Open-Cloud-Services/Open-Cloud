package app.open.software.rest

import app.open.software.rest.version.RestVersion
import io.ktor.application.install
import io.ktor.features.Compression
import io.ktor.features.DefaultHeaders
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import java.util.concurrent.TimeUnit

class WebServer(private val port: Int, private val versionList: List<RestVersion>) {

    private var server: NettyApplicationEngine? = null

    init {
        this.server = embeddedServer(Netty, this.port) {
            install(DefaultHeaders)
            install(Compression)

            routing {
                versionList.forEach {
                    it.setupRoutes().invoke(this)
                }
            }
        }
    }

    fun start() {
        this.server?.start(true)
    }

    fun stop() {
        //TODO set time values
        this.server?.stop(Long.MAX_VALUE, 0, TimeUnit.MILLISECONDS)
    }

}