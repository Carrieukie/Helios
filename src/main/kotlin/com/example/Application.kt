package com.example

import com.example.configurations.*
import io.ktor.server.application.*


fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)


@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureApplication()
    configureDatabases()
}
