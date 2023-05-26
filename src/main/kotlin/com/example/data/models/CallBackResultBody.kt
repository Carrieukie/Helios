package com.example.data.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNames

@Serializable
data class CallBackBody @OptIn(ExperimentalSerializationApi::class) constructor(
	@JsonNames("Body")
	val body: Body?
)

@Serializable
data class Body @OptIn(ExperimentalSerializationApi::class) constructor(
	@JsonNames("stkCallback")
	val stkCallback: StkCallback
)

@Serializable
data class StkCallback @OptIn(ExperimentalSerializationApi::class) constructor(
	@JsonNames("MerchantRequestID")
	val merchantRequestID: String,

	@JsonNames("CheckoutRequestID")
	val checkoutRequestID: String,

	@JsonNames("ResultDesc")
	val resultDesc: String,

	@JsonNames("ResultCode")
	val resultCode: Int,

	@JsonNames("CallbackMetadata")
	val callbackMetadata: CallbackRequestMetadata?
)
@Serializable
data class CallbackRequestMetadata @OptIn(ExperimentalSerializationApi::class) constructor(
	@JsonNames("Item")
	val item: List<ItemItem>
)

@Serializable
data class ItemItem @OptIn(ExperimentalSerializationApi::class) constructor(
	@JsonNames("Value")
	val value: JsonElement? = null,

	@JsonNames("Name")
	val name: String
)
