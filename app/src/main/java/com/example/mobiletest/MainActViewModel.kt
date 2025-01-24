package com.example.mobiletest

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiletest.model.BookingData
import com.example.mobiletest.repo.BookingRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
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
            //check if can use cache
            if (canUseCache()) {
                val cache = fetchBookingFromCache()
                if (cache != null) {
                    updateUi(cache)
                    return@launch
                }
            }
            //fetch data from network
            val result = bookingRepository.fetchBookingData()
            Log.i("MainActViewModel", "fetchBookingData: $result")
            if (result.isSuccessful) {
                Log.i("MainActViewModel", "fetchBookingData: ${result.body()}")
                val data = result.body() ?: return@launch
                //update cache
                bookingRepository.updateCacheExpireTime(data.expiryTime)
                bookingRepository.saveBookingData(data)
                updateUi(data)
            } else {
                _uiStateFlow.value =
                    MainPageUiState.Error(Throwable("Error fetching data : code ${result.code()}"))
            }
        }
    }

    private fun updateUi(data: BookingData) {
        data.segments.let {
            _uiStateFlow.value =
                MainPageUiState.Success(it.map { it.originAndDestinationPair.origin.displayName })
        }
    }

    suspend fun fetchBookingFromCache(): BookingData? {
        val result = bookingRepository.fetchBookingFromCache().first()
        val bookingData = Gson().fromJson(result, BookingData::class.java)
        return bookingData
    }

    suspend fun canUseCache(): Boolean {
        val expireTime = bookingRepository.getCacheExpireTime().first()
        return expireTime > System.currentTimeMillis() / 1000
    }

}

sealed interface MainPageUiState {
    object Loading : MainPageUiState
    data class Error(val throwable: Throwable) : MainPageUiState
    data class Success(val data: List<String>) : MainPageUiState
}