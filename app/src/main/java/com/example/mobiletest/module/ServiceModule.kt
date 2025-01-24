package com.example.mobiletest.module

import com.example.mobiletest.service.BookingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @author kraos
 * @date 2025/1/23
 */
@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {


    @Provides
    fun provideBookingService(
        retrofit: Retrofit,
    ): BookingService {
        return retrofit.create(BookingService::class.java)
    }
}
