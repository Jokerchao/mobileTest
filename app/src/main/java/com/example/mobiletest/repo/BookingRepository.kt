package com.example.mobiletest.repo

import android.app.Application
import com.example.mobiletest.MyApplication
import com.example.mobiletest.model.BookingData
import com.example.mobiletest.service.BookingService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author kraos
 * @date 2025/1/23
 */
//interface BookingRepository {
//    suspend fun fetchBookingData(): BookingData
//}

@Singleton
class BookingRepository @Inject constructor(
    private val service: BookingService
) {


    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface BookingRepositoryProviderEntryPoint {
        fun getBookingRepository(): BookingRepository
    }


    suspend fun fetchBookingData() = service.fetchBookingData()

}


