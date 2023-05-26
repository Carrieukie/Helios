package com.example.routes

import com.example.data.models.CallBackBody
import com.example.service.IResultsService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.callBack(){
    val resultService by inject<IResultsService>()

    post("/sendCallBack") {
        val body = call.receive<CallBackBody>()
        body.body?.stkCallback?.let { it1 -> resultService.insertCallBackResult(it1) }
        call.respond("success")
    }

    get("/allTransActions") {
        call.respond(resultService.fetchAllTheTranactions())
    }


}
