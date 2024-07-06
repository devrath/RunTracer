package com.istudio.auth.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import com.istudio.auth.presentation.register.RegisterViewModel
import com.istudio.auth.presentation.login.LoginScreenRoot
import com.istudio.auth.presentation.login.LoginViewModel

val authViewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::LoginViewModel)
}