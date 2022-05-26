package com.example.nasaapi.di.components

import com.example.nasaapi.di.annotations.PodScope
import com.example.nasaapi.di.modules.scopes.PodModule
import com.example.nasaapi.ui.pictureofthedayfragment.PictureOfTheDayFragment
import dagger.Subcomponent

@PodScope
@Subcomponent(modules = [PodModule::class])
interface PodSubcomponent {

    fun inject(fragment: PictureOfTheDayFragment)
}