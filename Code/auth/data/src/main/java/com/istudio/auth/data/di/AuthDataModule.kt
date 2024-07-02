package com.istudio.auth.data.di

import com.istudio.auth.data.EmailPatternValidator
import com.istudio.auth.domain.AuthRepository
import com.istudio.auth.domain.PatternValidator
import com.istudio.auth.domain.UserDataValidator
import com.istudio.auth.data.AuthRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    // Creates a EmailPatternValidator instance and returns it as a PatternValidator instance
    single<PatternValidator> {
        EmailPatternValidator
    }
    // Creates a instance of UserDataValidator
    singleOf(::UserDataValidator)
    // Creates a instance of AuthRepositoryImpl and return as AuthRepository instance
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
}