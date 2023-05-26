package com.example.data.repository.metadatarepository

import com.example.data.models.ResultMetadata
import com.example.data.tables.MetadataTable
import com.example.data.tables.MetadataTable.checkoutRequestID
import com.example.data.tables.MetadataTable.name
import com.example.data.tables.MetadataTable.value
import com.example.utils.dbQuery
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.select

class MetaDataRepository : IMetadataRepo {

    override suspend fun fetchResultMetadata(checkoutRequestID: String): List<ResultMetadata> = dbQuery {
         MetadataTable
            .select { MetadataTable.checkoutRequestID eq checkoutRequestID }
            .map {
                ResultMetadata(
                    id = it[MetadataTable.id].value,
                    name = it[MetadataTable.name],
                    value = it[MetadataTable.value] ?: "null",
                    checkoutRequestID = it[MetadataTable.checkoutRequestID]
                )
            }
    }

    override suspend fun saveResultMetadata(callbackMetadata: List<ResultMetadata>) = dbQuery{
        MetadataTable.batchInsert(callbackMetadata) { callbackMetadata ->
            this[name] = callbackMetadata.name
            this[value] = callbackMetadata.value
            this[checkoutRequestID] = callbackMetadata.checkoutRequestID
        }
    }

}