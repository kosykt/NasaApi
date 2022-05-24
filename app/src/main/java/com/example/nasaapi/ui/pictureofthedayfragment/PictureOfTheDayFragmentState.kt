package com.example.nasaapi.ui.pictureofthedayfragment

import com.example.nasaapi.domain.model.DomainPodModel

sealed class PictureOfTheDayFragmentState {

    data class Success(val response: DomainPodModel) : PictureOfTheDayFragmentState()
    data class Error(val error: String) : PictureOfTheDayFragmentState()
    object Loading : PictureOfTheDayFragmentState()
}