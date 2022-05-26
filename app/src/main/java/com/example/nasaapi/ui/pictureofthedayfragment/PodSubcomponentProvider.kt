package com.example.nasaapi.ui.pictureofthedayfragment

import com.example.nasaapi.di.components.PodSubcomponent

interface PodSubcomponentProvider {

    fun initPodSubcomponent(): PodSubcomponent
    fun destroyPodSubcomponent()
}