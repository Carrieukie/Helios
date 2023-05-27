package com.example.data.tables

import com.example.data.models.HeliosStkCallBackResponse
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.time.LocalDateTime

object ResultTable : IntIdTable() {
    val merchantRequestID = varchar(name = "MerchantRequestID", length = 512).uniqueIndex()
    val checkoutRequestID = varchar(name = "CheckoutRequestID", length = 512).uniqueIndex()
    val resultCode = integer(name = "ResultCode")
    val resultDesc = varchar(name = "ResultDesc", length = 512)
    val transActionCreateDate = varchar("transactioncreatedate", length = 255).nullable()
}

class ResultTableEntity(id: EntityID<Int>) : IntEntity(id) {
    var merchantRequestID by ResultTable.merchantRequestID
    var checkoutRequestID by ResultTable.checkoutRequestID
    var resultCode by ResultTable.resultCode
    var resultDesc by ResultTable.resultDesc
    var transActionCreateDate by ResultTable.transActionCreateDate

    val metadata by MetadataTableEntity referrersOn MetadataTable.result

    companion object : IntEntityClass<ResultTableEntity>(ResultTable)
}

fun ResultTableEntity.heliosStkCallBackResponse(): HeliosStkCallBackResponse {
    return HeliosStkCallBackResponse(
        merchantRequestID = merchantRequestID,
        checkoutRequestID = checkoutRequestID,
        resultDesc = resultDesc,
        resultCode = resultCode,
        callbackCreateDate = transActionCreateDate ?: LocalDateTime.now().toString(),
        callbackMetadata = metadata.map { it.toListItem() }
    )
}


