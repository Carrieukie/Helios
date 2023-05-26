package com.example.service

import com.example.data.models.*
import com.example.data.repository.metadatarepository.IMetadataRepo
import com.example.data.repository.resultrepository.IResultRepo
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import org.jetbrains.exposed.sql.transactions.transaction

class ResultsService(
    private val metaDataRepository: IMetadataRepo,
    private val resultRepository: IResultRepo
) : IResultsService {
    override suspend fun insertCallBackResult(stkCallback: StkCallback) {
        // extract body and insert
        val resultBody = stkCallback.toResultBody()
        resultRepository.insertResultBody(resultBody)

        // extract metadata and insert
        val callbackMetadata = stkCallback.callbackMetadata?.item ?: mutableListOf()
        val resultMetaData = callbackMetadata
            .map {
                it.toResultMetaData(checkoutRequestID = stkCallback.checkoutRequestID)
            }.toList()

        metaDataRepository.saveResultMetadata(resultMetaData)
    }

    override suspend fun fetchAllTheTranactions(): List<StkCallback> {
        return resultRepository
            .fetchAllTransactions()
            .map { resultBody ->

                val metaData = metaDataRepository.fetchResultMetadata(resultBody.merchantRequestID)

                StkCallback(
                    merchantRequestID = resultBody.merchantRequestID,
                    checkoutRequestID = resultBody.checkoutRequestID,
                    resultDesc = resultBody.resultDesc,
                    resultCode = resultBody.resultCode,
                    callbackMetadata = CallbackRequestMetadata(
                        metaData.map {
                            ItemItem(
                                value = JsonPrimitive(it.value),
                                name = it.name
                            )
                        }
                    )
                )
            }
    }
}

