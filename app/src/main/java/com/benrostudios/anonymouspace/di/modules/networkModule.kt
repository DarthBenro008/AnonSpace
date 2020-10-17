package com.benrostudios.anonymouspace.di.modules

import com.benrostudios.anonymouspace.data.network.NetworkService
import org.koin.dsl.module

val networkModule = module {
    single { NetworkService(get()) }
}