package com.istudio.auth.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.istudio.auth.presentation.register.RegisterViewModel

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
}