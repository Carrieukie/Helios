package com.example.di

import com.example.data.repository.resultrepository.IResultRepo
import com.example.data.repository.resultrepository.ResultRepository
import com.example.service.IResultsService
import com.example.service.ResultsService
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single<IResultRepo> { ResultRepository() }
    single<IResultsService> { ResultsService(get()) }
}

val jsonModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
            useArrayPolymorphism = true
        }
    }
}