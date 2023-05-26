package com.example.configurations

import com.example.di.appModule
import com.example.di.jsonModule
import com.example.exceptions.ExceptionResponse
import com.example.routes.callBack
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
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
        exception<Throwable> { call, throwable ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ExceptionResponse(
                    message = throwable.localizedMessage,
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

//    install(CORS) {
//        anyHost()
//        allowHeader(HttpHeaders.ContentType)
//    }
//
//    install(SwaggerUI) {
//        swagger {
//            swaggerUrl = "swagger-ui"
//            forwardRoot = true
//        }
//        info {
//            title = "Example API"
//            version = "latest"
//            description = "Example API for testing and demonstration purposes."
//        }
//        server {
//            url = "http://localhost:8080"
//            description = "Development Server"
//        }
//    }


    routing {
        callBack()

    }

}