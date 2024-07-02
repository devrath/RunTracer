package com.istudio.core.data.networking.di

import com.istudio.core.data.networking.networking.HttpClientFactory
import com.istudio.core.data.networking.auth.EncryptedSessionStorage
import com.istudio.core.domain.SessionStorage
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
}