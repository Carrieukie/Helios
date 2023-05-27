package com.example.data.repository.resultrepository

import com.example.data.models.ResultBody
import com.example.data.models.StkCallback
import com.example.data.tables.ResultTableEntity


interface IResultRepo {

    suspend fun insertResultBody(resultBody: ResultBody): Long
    suspend fun fetchAllTransactions(): List<StkCallback>

}