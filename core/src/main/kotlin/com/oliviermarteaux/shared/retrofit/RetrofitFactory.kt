package com.oliviermarteaux.shared.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * A singleton factory object for creating Retrofit instances configured for JSON serialization.
 *
 * Provides a convenient way to create [Retrofit] clients with Kotlinx Serialization support.
 *
 * ### Usage:
 * ```kotlin
 * val retrofit = RetrofitFactory.createFromUrl("https://api.example.com/")
 * val apiService = retrofit.create(MyApiService::class.java)
 * ```
 *
 * ### Properties:
 * - [json]: Configured [Json] instance that ignores unknown keys in responses.
 *
 * ### Functions:
 * - [createFromUrl]: Creates and returns a [Retrofit] instance for the specified base URL.
 */
object RetrofitFactory {

    private val json = Json { ignoreUnknownKeys = true }

    fun createFromUrl(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}