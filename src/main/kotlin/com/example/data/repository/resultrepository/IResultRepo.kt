package com.example.data.repository.resultrepository

import com.example.data.models.ResultBody


interface IResultRepo {

    suspend fun insertResultBody(resultBody: ResultBody)
    suspend fun fetchAllTransactions(): List<ResultBody>

}