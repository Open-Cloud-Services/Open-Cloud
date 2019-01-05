package app.open.software.rest

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.netty.NettyApplicationEngine
import java.util.concurrent.TimeUnit

class WebServer (private val port: Int){

    private var server: NettyApplicationEngine? = null

    init {
        this.server = embeddedServer(Netty, this.port) {

        }
    }

    fun start() {
        this.server?.start(true)
    }

    fun stop() {
        this.server?.stop(Long.MAX_VALUE, 0, TimeUnit.MILLISECONDS)
    }

}