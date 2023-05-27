package com.example.data.repository.resultrepository

import com.example.data.models.ResultBody
import com.example.data.models.StkCallback
import com.example.data.tables.*
import com.example.utils.dbQuery
import org.jetbrains.exposed.sql.selectAll

class ResultRepository : IResultRepo {
    override suspend fun insertResultBody(resultBody: ResultBody): Long = dbQuery {

        ResultTableEntity.new {
            merchantRequestID = resultBody.merchantRequestID
            checkoutRequestID = resultBody.checkoutRequestID
            resultCode = resultBody.resultCode
            resultDesc = resultBody.resultDesc
        }.id.value.toLong()
    }

    override suspend fun fetchAllTransactions(): List<StkCallback> = dbQuery {
        ResultTableEntity
            .all()
            .map { it.toStkCallback() }
    }

}