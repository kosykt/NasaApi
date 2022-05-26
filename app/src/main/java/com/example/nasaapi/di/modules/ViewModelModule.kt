package com.example.nasaapi.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.nasaapi.utils.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}