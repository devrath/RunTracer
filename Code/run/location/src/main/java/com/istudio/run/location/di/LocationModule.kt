package com.istudio.run.location.di

import com.istudio.run.domain.LocationObserver
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.istudio.run.location.AndroidLocationObserver
import org.koin.dsl.bind

val locationModule = module {
    singleOf(::AndroidLocationObserver).bind<LocationObserver>()
}