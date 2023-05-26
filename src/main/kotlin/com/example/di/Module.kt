package com.example.di

import com.example.data.repository.metadatarepository.MetaDataRepository
import com.example.data.repository.resultrepository.ResultRepository
import com.example.data.repository.metadatarepository.IMetadataRepo
import com.example.data.repository.resultrepository.IResultRepo
import com.example.service.IResultsService
import com.example.service.ResultsService
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single<IMetadataRepo> { MetaDataRepository() }
    single<IResultRepo> { ResultRepository() }
    single<IResultsService> { ResultsService(get(), get()) }
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