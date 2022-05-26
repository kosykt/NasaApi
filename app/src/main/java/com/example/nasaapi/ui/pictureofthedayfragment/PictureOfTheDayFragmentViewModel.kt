package com.example.nasaapi.ui.pictureofthedayfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteFavoritePodUseCase
import com.example.domain.GetAllFavoritePodsUseCase
import com.example.domain.GetPodUseCase
import com.example.domain.SaveFavoritePodUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class PictureOfTheDayFragmentViewModel @Inject constructor(
    private val podSubcomponentProvider: PodSubcomponentProvider,
    private val getPodUseCase: GetPodUseCase,
    private val saveFavoritePodUseCase: SaveFavoritePodUseCase,
    private val deleteFavoritePodUseCase: DeleteFavoritePodUseCase,
    getAllFavoritePodsUseCase: GetAllFavoritePodsUseCase,
) : ViewModel() {

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

    override fun onCleared() {
        super.onCleared()
        podSubcomponentProvider.destroyPodSubcomponent()
    }
}