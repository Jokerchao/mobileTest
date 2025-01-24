package com.example.mobiletest.service

import android.util.Log
import com.example.mobiletest.MyApplication
import com.example.mobiletest.model.BookingData
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.mock.BehaviorDelegate

/**
 * Booking service for request
 */
interface BookingService {
    @GET("/api/booking")
    suspend fun fetchBookingData(): Response<BookingData>

}

class MockBookingService(
    private val delegate: BehaviorDelegate<BookingService>
) : BookingService {
    override suspend fun fetchBookingData(): Response<BookingData> {
        val json = MyApplication.context.applicationContext.assets.open("booking.json").bufferedReader().use {
            it.readText()
        }
        Log.i("MockBookingService", "fetchBookingData: $json")
        val bookingData = Gson().fromJson(json, BookingData::class.java)

        // user BehaviorDelegate mock success response
        return delegate.returningResponse(bookingData).fetchBookingData()
    }
}