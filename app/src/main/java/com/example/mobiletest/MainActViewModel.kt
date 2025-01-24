package com.example.mobiletest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletest.repo.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

/**
 * @author kraos
 * @date 2025/1/23
 */
@HiltViewModel
class MainActViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
) : ViewModel() {


    private val _uiStateFlow = MutableStateFlow<MainPageUiState>(MainPageUiState.Loading)
    val uiStateFlow: StateFlow<MainPageUiState> = _uiStateFlow

    fun fetchBookingData() {
        _uiStateFlow.value = MainPageUiState.Loading
        viewModelScope.launch {
            //mock success
            val isSuccess = Random.nextBoolean()
            if (isSuccess) {
                _uiStateFlow.value = MainPageUiState.Success(
                    listOf(
                        "Ship 1",
                        "Ship2",
                        "Ship3",
                        "Ship4",
                        "Ship5",
                        "Ship6"
                    )
                )
                return@launch
            }
            val result = bookingRepository.fetchBookingData()
            if (result.isSuccessful) {
                val data = result.body() ?: return@launch
                data.segments.let {
                    _uiStateFlow.value =
                        MainPageUiState.Success(it.map { it.originAndDestinationPair.origin.displayName })
                }
                _uiStateFlow.value = MainPageUiState.Success(listOf("Ship 1", "Ship2", "Ship3"))
            } else {
                _uiStateFlow.value = MainPageUiState.Error(Throwable("Error fetching data"))
            }
        }
    }

}

sealed interface MainPageUiState {
    object Loading : MainPageUiState
    data class Error(val throwable: Throwable) : MainPageUiState
    data class Success(val data: List<String>) : MainPageUiState
}