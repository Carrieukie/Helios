package com.example.data.models

data class ResultMetadata (
    val id : Int? = null,
    val name : String,
    val value : String,
)

fun ItemItem.toResultMetaData(checkoutRequestID: String): ResultMetadata {
    return ResultMetadata(
        name = name,
        value = value.toString().replace("\"",""),
    )
}