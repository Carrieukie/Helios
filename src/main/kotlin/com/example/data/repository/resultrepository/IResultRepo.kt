package com.example.data.repository.resultrepository

import com.example.data.models.HeliosStkCallBackResponse
import com.example.data.models.StkCallback
import com.example.data.tables.ResultTableEntity


interface IResultRepo {

    suspend fun insertSTKBody(resultBody: StkCallback): HeliosStkCallBackResponse
    suspend fun fetchAllTransactions(): List<HeliosStkCallBackResponse>

}