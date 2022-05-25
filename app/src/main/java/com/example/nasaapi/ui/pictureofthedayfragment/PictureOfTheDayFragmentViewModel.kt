package com.example.nasaapi.ui.pictureofthedayfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaapi.data.database.AppDatabase
import com.example.nasaapi.data.database.DatabaseRepositoryImpl
import com.example.nasaapi.data.network.ApiHolder
import com.example.nasaapi.data.network.NetworkRepositoryImpl
import com.example.nasaapi.data.repository.DomainRepositoryImpl
import com.example.nasaapi.domain.DeleteFavoritePodUseCase
import com.example.nasaapi.domain.GetAllFavoritePodsUseCase
import com.example.nasaapi.domain.GetPodUseCase
import com.example.nasaapi.domain.SaveFavoritePodUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class PictureOfTheDayFragmentViewModel : ViewModel() {

    private val db = AppDatabase.instance
    private val databaseRepository = DatabaseRepositoryImpl(db)
    private val retrofitService = ApiHolder.retrofitService
    private val networkRepository = NetworkRepositoryImpl(retrofitService)
    private val domainRepository = DomainRepositoryImpl(networkRepository, databaseRepository)
    private val getPodUseCase = GetPodUseCase(domainRepository)
    private val saveFavoritePodUseCase = SaveFavoritePodUseCase(domainRepository)
    private val deleteFavoritePodUseCase = DeleteFavoritePodUseCase(domainRepository)
    private val getAllFavoritePodsUseCase = GetAllFavoritePodsUseCase(domainRepository)

    private var _actualDate = LocalDate.now().minusDays(1)
    val actualDate: LocalDate
        get() = _actualDate

    private val favoritePods: StateFlow<List<String>> = getAllFavoritePodsUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    private val _pod: MutableStateFlow<PictureOfTheDayFragmentState> =
        MutableStateFlow(PictureOfTheDayFragmentState.Success())
    val pod: StateFlow<PictureOfTheDayFragmentState> = _pod.asStateFlow()

    fun getPod(isNetworkAvailable: Boolean, date: String) {
        _pod.value = PictureOfTheDayFragmentState.Loading
        viewModelScope.launch(
            Dispatchers.IO
                    + CoroutineExceptionHandler { _, throwable ->
                errorCatch(throwable)
            }
        ) {
            _pod.value = PictureOfTheDayFragmentState.Success(
                getPodUseCase.execute(
                    isNetworkAvailable,
                    date
                )
            )
        }
    }

    fun checkIfFavorite(): Boolean {
        return favoritePods.value.contains(actualDate.toString())
    }


    fun favoriteClickHandler(): Boolean {
        return when (checkIfFavorite()) {
            true -> {
                viewModelScope.launch(Dispatchers.IO) {
                    deleteFavoritePodUseCase.execute(actualDate.toString())
                }
                false
            }
            false -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveFavoritePodUseCase.execute(actualDate.toString())
                }
                true
            }
        }
    }

    fun minusDay() {
        _actualDate = _actualDate.minusDays(1)
    }

    fun plusDay() {
        _actualDate = _actualDate.plusDays(1)
    }

    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        _actualDate = LocalDate.of(year, month, dayOfMonth)
    }

    private fun errorCatch(throwable: Throwable) {
        _pod.value = PictureOfTheDayFragmentState.Error(throwable.message.toString())
    }
}