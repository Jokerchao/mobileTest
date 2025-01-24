package com.example.mobiletest.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * local cache for booking data ,can replace with Room or other local storage
 */
class BookingDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val BOOK_DATA_EXPIRE_TIME = "book_data_expire_time"

        private const val BOOK_DATA_CACHE_KEY = "book_data_cache_key"
    }

    private val Context.dataStore by preferencesDataStore(
        name = "booking_data_store"
    )

    val bookDataExpireTime = context.dataStore.data.map {
        it[longPreferencesKey(BOOK_DATA_EXPIRE_TIME)] ?: 0L
    }

    suspend fun updateBookDataExpireTime(
        expireTime: Long
    ) {
        val key = BOOK_DATA_EXPIRE_TIME
        context.dataStore.edit { it[longPreferencesKey(key)] = expireTime }
    }

    val bookDataCache = context.dataStore.data.map {
        it[stringPreferencesKey(BOOK_DATA_CACHE_KEY)] ?: ""
    }

    /**
     * update booking data cache
     */
    suspend fun updateBookDataCache(
        cache: String
    ) {
        val key = BOOK_DATA_CACHE_KEY
        context.dataStore.edit { it[stringPreferencesKey(key)] = cache }
    }


}