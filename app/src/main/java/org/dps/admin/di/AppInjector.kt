package org.dps.admin.di

import org.dps.admin.mvvm.AssignClassTeacherViewModel
import org.dps.admin.mvvm.ClassViewModel
import org.dps.admin.mvvm.MainViewModel
import org.dps.admin.network.RestClient
import org.dps.admin.network.RestClient.webServices
import org.dps.admin.repository.Repository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // a single instance for Repository class
    single { Repository(get()) }
}

val networkModule = module {
    single { RestClient }
    single { webServices() }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
        ClassViewModel(get())
        AssignClassTeacherViewModel(get())
    }
}