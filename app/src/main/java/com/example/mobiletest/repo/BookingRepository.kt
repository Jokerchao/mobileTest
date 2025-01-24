package com.example.mobiletest.repo

import android.app.Application
import com.example.mobiletest.MyApplication
import com.example.mobiletest.local.BookingDataStore
import com.example.mobiletest.model.BookingData
import com.example.mobiletest.service.BookingService
import com.google.gson.Gson
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
@Singleton
class BookingRepository @Inject constructor(
    private val service: BookingService,
    private val dataStore: BookingDataStore
) {


    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface BookingRepositoryProviderEntryPoint {
        fun getBookingRepository(): BookingRepository
    }


    suspend fun fetchBookingData() = service.fetchBookingData()

    fun fetchBookingFromCache() = dataStore.bookDataCache

    suspend fun saveBookingData(bookingData: BookingData) {
        val gsonString = Gson().toJson(bookingData)
        dataStore.updateBookDataCache(gsonString)
    }

    /**
     * get cache expire time
     */
    fun getCacheExpireTime() = dataStore.bookDataExpireTime

    /**
     * update cache expire time
     */
    suspend fun updateCacheExpireTime(expireTime: Long) {
        dataStore.updateBookDataExpireTime(expireTime)
    }

}


