package com.example.data.repository.metadatarepository

import com.example.data.models.ItemItem
import com.example.data.tables.MetadataTableEntity
import com.example.data.tables.ResultTableEntity
import com.example.utils.dbQuery

class MetaDataRepository : IMetadataRepo {

     override suspend fun saveResultMetadata(resultBody: Long, item: List<ItemItem>): Unit = dbQuery{
         val resultTable = try {
             ResultTableEntity[resultBody.toInt()]
         }catch (e: Exception){
             null
         }

         resultTable?.let {
             item.forEach { resultMetadata ->
                 MetadataTableEntity.new {
                     name = resultMetadata.name
                     value = resultMetadata.value.toString()
                     result = resultTable
                 }
             }
         }

    }

}