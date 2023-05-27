package com.example.service

import com.example.data.models.*
import com.example.data.repository.metadatarepository.IMetadataRepo
import com.example.data.repository.resultrepository.IResultRepo

class ResultsService(
    private val metaDataRepository: IMetadataRepo,
    private val resultRepository: IResultRepo
) : IResultsService {
    override suspend fun insertCallBackResult(stkCallback: StkCallback) {
        // extract body and insert
        val resultBody = stkCallback.toResultBody()
        val resultBodyId = resultRepository.insertResultBody(resultBody)

        // extract metadata and insert
        val callbackMetadata = stkCallback.callbackMetadata?.item ?: mutableListOf()
        metaDataRepository.saveResultMetadata(resultBodyId, callbackMetadata)
    }

    override suspend fun fetchAllTheTranactions(): List<StkCallback> {
        return resultRepository
            .fetchAllTransactions()
    }
}

