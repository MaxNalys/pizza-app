package com.dev.data.remote

import com.dev.data.remote.model.PizzaResponse
import retrofit2.http.GET
import javax.inject.Inject


interface PizzaApiService {

    @GET("api/pizzas")

    suspend fun getPizzas(): PizzaResponse

}

class PizzaDataSource @Inject constructor(

    private val api: PizzaApiService

) {

    suspend fun getPizzas() = api.getPizzas()

}