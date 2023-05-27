package com.example.configurations

import com.example.di.appModule
import com.example.di.jsonModule
import com.example.exceptions.ExceptionResponse
import com.example.routes.callBack
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.swagger.codegen.v3.generators.html.StaticHtmlCodegen
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureApplication() {

    install(ContentNegotiation) {
        json(
            json = Json {
                ignoreUnknownKeys = true
                isLenient = true
                coerceInputValues = true
                useArrayPolymorphism = true
                explicitNulls = false
            }
        )
    }

    install(Koin) {
        slf4jLogger()
        modules(
            appModule,
            jsonModule
        )
    }

    install(StatusPages) {
        configureStatusPages()
    }

    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }


    routing {
        callBack()
        swaggerUI(path = "swagger", swaggerFile = "openapi/swagger.json")
    }

}

fun StatusPagesConfig.configureStatusPages(){
    exception<Throwable> { call, throwable ->
        throwable.printStackTrace()
        call.respond(
            status = HttpStatusCode.InternalServerError,
            message = ExceptionResponse(
                message = throwable.localizedMessage ?: throwable.message ?: throwable.cause?.localizedMessage ?: "Something went wrong",
                code = HttpStatusCode.InternalServerError.value
            )
        )
        throw throwable
    }
    status(
        HttpStatusCode.InternalServerError,
        HttpStatusCode.BadGateway,
        HttpStatusCode.NotFound,
        HttpStatusCode.BadRequest
    ) { call, statusCode ->
        when (statusCode) {
            HttpStatusCode.InternalServerError -> {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ExceptionResponse(
                        "Oops! internal server error at our end",
                        HttpStatusCode.InternalServerError.value
                    )
                )
            }

            HttpStatusCode.BadGateway -> {
                call.respond(
                    HttpStatusCode.BadGateway,
                    ExceptionResponse(
                        "Oops! We got a bad gateway. Fixing it. Hold on!",
                        HttpStatusCode.BadGateway.value
                    )
                )
            }

            HttpStatusCode.NotFound -> {
                call.respond(
                    HttpStatusCode.NotFound,
                    ExceptionResponse(
                        "Oops! We don't have that url!Please check it again.",
                        HttpStatusCode.NotFound.value
                    )
                )
            }
            HttpStatusCode.BadRequest -> {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ExceptionResponse(
                        "Oops! please check for malformed request syntax, invalid request message framing, or deceptive request routing",
                        HttpStatusCode.BadRequest.value
                    )
                )
            }
        }
    }
}