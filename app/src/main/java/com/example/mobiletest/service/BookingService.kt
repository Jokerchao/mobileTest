package com.example.mobiletest.service

import com.example.mobiletest.model.BookingData
import retrofit2.Response
import retrofit2.http.GET

/**
 * Booking service for request
 */
interface BookingService {


    @GET("/api/booking")
    suspend fun fetchBookingData(): Response<BookingData>


}