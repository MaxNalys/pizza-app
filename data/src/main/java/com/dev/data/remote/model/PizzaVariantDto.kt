package com.dev.data.remote.model

import kotlinx.serialization.SerialName

import kotlinx.serialization.Serializable

@Serializable
data class PizzaVariantDto(

    val size: String,
    val price: Double
)