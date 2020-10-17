package com.benrostudios.anonymouspace.di.modules

import com.benrostudios.anonymouspace.ui.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
}