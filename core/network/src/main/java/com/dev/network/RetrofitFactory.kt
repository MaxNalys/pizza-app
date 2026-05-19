package com.dev.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitFactory @Inject constructor(

    private val json: Json,

    private val client: OkHttpClient

) {

    fun create(baseUrl: String): Retrofit {

        return Retrofit.Builder()

            .baseUrl(baseUrl)

            .client(client)

            .addConverterFactory(

                json.asConverterFactory("application/json".toMediaType())

            )

            .build()

    }

}