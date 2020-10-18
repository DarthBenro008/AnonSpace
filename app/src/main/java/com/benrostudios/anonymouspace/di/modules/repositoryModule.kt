package com.benrostudios.anonymouspace.di.modules


import com.benrostudios.anonymouspace.data.repositories.NetworkRepo
import com.benrostudios.anonymouspace.data.repositories.NetworkRepoImpl
import com.benrostudios.anonymouspace.utils.NearbyApi
import com.benrostudios.anonymouspace.utils.SharedPrefManager
import org.koin.dsl.module

val repositoryModule = module {
    single { SharedPrefManager(get()) }
    single<NetworkRepo> { NetworkRepoImpl(get()) }
    //single { NearbyApi(get(), get()) }
}