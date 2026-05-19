package com.dev.model

data class Pizza(

    val id: String,

    val name: String,

    val description: String,

    val imageUrl: String,

    val variants: List<PizzaVariant>

)

data class PizzaVariant(

    val size: String,

    val price: Double

)