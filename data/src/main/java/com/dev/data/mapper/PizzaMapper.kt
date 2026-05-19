package com.dev.data.mapper

import com.dev.data.remote.model.PizzaDto
import com.dev.data.remote.model.PizzaVariantDto
import com.dev.model.Pizza
import com.dev.model.PizzaVariant

fun PizzaDto.toDomain(): Pizza {

    return Pizza(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        variants = variants.map {
            it.toDomain()
        }
    )
}

fun PizzaVariantDto.toDomain(): PizzaVariant {

    return PizzaVariant(
        size = size,
        price = price
    )
}