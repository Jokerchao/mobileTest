package com.example.mobiletest.module

import com.example.mobiletest.service.BookingService
import com.example.mobiletest.service.MockBookingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author kraos
 * @date 2025/1/23
 */
@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    private const val IS_MOCK = true

    @Provides
    fun provideBookingService(
        retrofit: Retrofit,
    ): BookingService {
        if (IS_MOCK) {
            val networkBehavior = NetworkBehavior.create().apply {
                setDelay(1, TimeUnit.SECONDS) // mock delay 2s
                setVariancePercent(50) // mock 50% variance in delay
                setErrorPercent(10)  // mock 10% error
                setFailurePercent(5) // mock 5% failure
            }
            val mockRetrofit = MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior)
                .build()
            val delegate = mockRetrofit.create(BookingService::class.java)
            return MockBookingService(delegate)
        }
        return retrofit.create(BookingService::class.java)
    }
}
