package com.example.data.repository.resultrepository

import com.example.data.models.HeliosStkCallBackResponse
import com.example.data.models.StkCallback
import com.example.data.tables.MetadataTableEntity
import com.example.data.tables.ResultTableEntity
import com.example.data.tables.heliosStkCallBackResponse
import com.example.utils.Logger
import com.example.utils.dbQuery
import com.example.utils.getCurrentTime
import java.time.LocalDateTime
import java.time.ZoneOffset

class ResultRepository : IResultRepo {
    override suspend fun insertSTKBody(stkCallback: StkCallback): HeliosStkCallBackResponse = dbQuery {
        Logger.info("Inserting stk callback checkoutRequestID : ${stkCallback.checkoutRequestID} at ${getCurrentTime()}")
        val resultBody = ResultTableEntity.new {
            merchantRequestID = stkCallback.merchantRequestID
            checkoutRequestID = stkCallback.checkoutRequestID
            resultCode = stkCallback.resultCode
            resultDesc = stkCallback.resultDesc
            transActionCreateDate = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString()
        }

        Logger.info("Inserted stkcallback result ID : ${resultBody.id} at ${getCurrentTime()}")

        stkCallback.callbackMetadata?.item?.forEach { resultMetadata ->
            Logger.info("Inserting $resultMetadata at ${getCurrentTime()}")
            MetadataTableEntity.new {
                name = resultMetadata.name
                value = resultMetadata.value.toString()
                result = resultBody
            }
        }
        return@dbQuery ResultTableEntity[resultBody.id.value].heliosStkCallBackResponse()
    }

    override suspend fun fetchAllTransactions(): List<HeliosStkCallBackResponse> = dbQuery {
        ResultTableEntity
            .all()
            .sortedByDescending { it.transActionCreateDate }
            .map { it.heliosStkCallBackResponse() }
    }

}