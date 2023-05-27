package com.example.routes

import com.example.data.models.SafaricomResultBody
import com.example.service.IResultsService
import com.example.utils.Logger
import com.example.utils.getCurrentTime
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.callBack(){
    val resultService by inject<IResultsService>()
    post("/sendCallBack") {
        val stkCallback = call.receive<SafaricomResultBody>().body.stkCallback
        Logger.info("received body at: ${getCurrentTime()} $stkCallback")
        val insertResponse = resultService.insertCallBackResult(stkCallback)
        call.respond(insertResponse)
    }

    get("/allTransActions") {
        call.respond(resultService.fetchAllTheTranactions())
    }

}


