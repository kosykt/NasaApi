package com.example.nasaapi.ui.pictureofthedayfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapi.data.database.AppDatabase
import com.example.nasaapi.data.database.DatabaseRepositoryImpl
import com.example.nasaapi.data.network.ApiHolder
import com.example.nasaapi.data.network.NetworkRepositoryImpl
import com.example.nasaapi.data.repository.DomainRepositoryImpl
import com.example.nasaapi.domain.GetPodUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PictureOfTheDayFragmentViewModel : ViewModel() {

    private val db = AppDatabase.instance
    private val databaseRepository = DatabaseRepositoryImpl(db)
    private val retrofitService = ApiHolder.retrofitService
    private val networkRepository = NetworkRepositoryImpl(retrofitService)
    private val domainRepository = DomainRepositoryImpl(networkRepository, databaseRepository)
    private val getPodUseCase = GetPodUseCase(domainRepository)

    private val _pod: MutableStateFlow<PictureOfTheDayFragmentState> =
        MutableStateFlow(PictureOfTheDayFragmentState.Success())
    val pod: StateFlow<PictureOfTheDayFragmentState> = _pod.asStateFlow()

    fun getPod(isNetworkAvailable: Boolean, date: String) {
        viewModelScope.coroutineContext.cancelChildren()
        _pod.value = PictureOfTheDayFragmentState.Loading
        viewModelScope.launch(
            Dispatchers.IO
                    + CoroutineExceptionHandler { _, throwable ->
                errorCatch(throwable)
            }
        ) {
            _pod.value = PictureOfTheDayFragmentState.Success(
                getPodUseCase(
                    isNetworkAvailable,
                    date
                )
            )
        }
    }

    private fun errorCatch(throwable: Throwable) {
        _pod.value = PictureOfTheDayFragmentState.Error(throwable.message.toString())
    }
}