package com.example.service

import com.example.data.models.*
import com.example.data.repository.resultrepository.IResultRepo

class ResultsService(
    private val resultRepository: IResultRepo
) : IResultsService {
    override suspend fun insertCallBackResult(stkCallback: StkCallback): HeliosStkCallBackResponse {
        return resultRepository.insertSTKBody(stkCallback)
    }

    override suspend fun fetchAllTheTranactions(): List<HeliosStkCallBackResponse> {
        return resultRepository
            .fetchAllTransactions()
    }
}

