package com.example.data.models

data class ResultBody(
    val id: Int? = null,
    val merchantRequestID: String,
    val checkoutRequestID: String,
    val resultCode: Int,
    val resultDesc: String,
)

fun StkCallback.toResultBody(): ResultBody {
    return ResultBody(
        merchantRequestID = merchantRequestID,
        checkoutRequestID = checkoutRequestID,
        resultCode = resultCode,
        resultDesc = resultDesc,
    )
}