package com.benrostudios.anonymouspace.di

import com.benrostudios.anonymouspace.di.modules.networkModule
import com.benrostudios.anonymouspace.di.modules.repositoryModule
import com.benrostudios.anonymouspace.di.modules.viewModelModule

val appComponent = listOf(
    networkModule,
    repositoryModule,
    viewModelModule
)