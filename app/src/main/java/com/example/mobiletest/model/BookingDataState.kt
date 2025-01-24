package com.example.mobiletest.model

/**
 * BookingData Ui state
 */
sealed class BookingDataState {
    object Loading : BookingDataState()
    data class Success(val data: BookingData) : BookingDataState()
    data class Error(val message: String) : BookingDataState()
}