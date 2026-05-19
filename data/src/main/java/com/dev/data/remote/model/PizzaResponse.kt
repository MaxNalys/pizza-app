package com.dev.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PizzaResponse(

    @SerialName("pizzas")
    val pizzas: List<PizzaDto>
)