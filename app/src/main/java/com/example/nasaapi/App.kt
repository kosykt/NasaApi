package com.example.nasaapi

import android.app.Application
import com.example.nasaapi.di.components.AppComponent
import com.example.nasaapi.di.components.DaggerAppComponent
import com.example.nasaapi.di.components.PodSubcomponent
import com.example.nasaapi.di.modules.singletones.AppModule
import com.example.nasaapi.ui.pictureofthedayfragment.PodSubcomponentProvider

class App: Application(), PodSubcomponentProvider {

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private var podSubcomponent: PodSubcomponent? = null

    override fun initPodSubcomponent(): PodSubcomponent = appComponent
        .providePodSubcomponent()
        .also {
            podSubcomponent = it
        }

    override fun destroyPodSubcomponent() {
        podSubcomponent = null
    }


}