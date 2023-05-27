package com.example.data.repository.metadatarepository

import com.example.data.models.ItemItem

interface IMetadataRepo {
    suspend fun saveResultMetadata(resultBody: Long, item: List<ItemItem>)
}