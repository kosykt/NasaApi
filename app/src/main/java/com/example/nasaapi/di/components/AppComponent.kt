package com.example.nasaapi.di.components

import com.example.nasaapi.di.modules.ViewModelModule
import com.example.nasaapi.di.modules.singletones.AppModule
import com.example.nasaapi.di.modules.singletones.RetrofitModule
import com.example.nasaapi.di.modules.singletones.RoomModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RoomModule::class,
        RetrofitModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun providePodSubcomponent(): PodSubcomponent
}