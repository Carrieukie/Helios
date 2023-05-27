package com.example.data.models

import kotlinx.serialization.Serializable

@Serializable
data class HeliosStkCallBackResponse(
    val merchantRequestID: String,
    val checkoutRequestID: String,
    val resultDesc: String,
    val resultCode: Int,
    val callbackCreateDate: String,
    val callbackMetadata: List<ItemItem>
)