package com.example.data.repository.metadatarepository

import com.example.data.models.ResultMetadata
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

interface IMetadataRepo {

    suspend fun fetchResultMetadata(checkoutRequestID: String): List<ResultMetadata>

    suspend fun saveResultMetadata(callbackMetadata: List<ResultMetadata>): List<ResultRow>
}