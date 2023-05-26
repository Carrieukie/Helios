package com.example.data.repository.resultrepository

import com.example.data.models.ResultBody
import com.example.data.tables.MetadataTable
import com.example.data.tables.ResultTable
import com.example.utils.dbQuery
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ResultRepository(): IResultRepo {
    override suspend fun insertResultBody(resultBody: ResultBody): Unit = dbQuery{
        ResultTable.insert {
            it[merchantRequestID] = resultBody.merchantRequestID
            it[checkoutRequestID] = resultBody.checkoutRequestID
            it[resultCode] = resultBody.resultCode
            it[resultDesc] = resultBody.resultDesc
        }
    }

    override suspend fun fetchAllTransactions(): List<ResultBody>  = dbQuery{

        ResultTable
           .selectAll()
           .map {
           ResultBody(
               id = it[ResultTable.id].value,
               merchantRequestID = it[ResultTable.merchantRequestID],
               checkoutRequestID = it[ResultTable.checkoutRequestID],
               resultCode = it[ResultTable.resultCode],
               resultDesc = it[ResultTable.resultDesc],
           )
       }
    }

}