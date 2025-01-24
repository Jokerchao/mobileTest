package com.example.mobiletest.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * BookingData Entity,response from server
 */
data class BookingData(
    @SerializedName("bookingReference")
    val shipReference: String = "",
    @SerializedName("token")
    val shipToken: String = "",
    //time for the token to expire
    @SerializedName("expiryTime")
    val expiryTime: Long = 0L,
    @SerializedName("duration")
    val duration: Int = 0,
    @SerializedName("segments")
    val segments: List<Segment>
): Serializable

data class Segment(
    @SerializedName("id")
    val id: Int,
    //information about the origin and destination of the segment
    @SerializedName("originAndDestinationPair")
    val originAndDestinationPair: OriginAndDestinationPair
): Serializable

data class OriginAndDestinationPair(
    @SerializedName("origin")
    val origin: Location,
    @SerializedName("destination")
    val destination: Location
): Serializable

data class Location(
    @SerializedName("code")
    val code: String,
    @SerializedName("displayName")
    val displayName: String,
    @SerializedName("timeZone")
    val url: String
): Serializable