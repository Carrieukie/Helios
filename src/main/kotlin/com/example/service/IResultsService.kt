package com.example.service

import com.example.data.models.HeliosStkCallBackResponse
import com.example.data.models.StkCallback

interface IResultsService {
    suspend fun insertCallBackResult(stkCallback: StkCallback): HeliosStkCallBackResponse
    suspend fun fetchAllTheTranactions(): List<HeliosStkCallBackResponse>
}