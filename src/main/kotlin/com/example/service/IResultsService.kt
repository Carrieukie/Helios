package com.example.service

import com.example.data.models.StkCallback

interface IResultsService {
    suspend fun insertCallBackResult(stkCallback: StkCallback)
    suspend fun fetchAllTheTranactions(): List<StkCallback>
}