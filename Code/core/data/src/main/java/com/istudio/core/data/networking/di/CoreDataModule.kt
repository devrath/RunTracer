package com.istudio.core.data.networking.di

import com.istudio.core.data.networking.networking.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}