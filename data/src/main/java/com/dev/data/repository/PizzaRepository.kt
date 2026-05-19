package com.dev.data.repository

import com.dev.data.mapper.toDomain
import com.dev.data.remote.PizzaDataSource
import com.dev.model.Pizza
import javax.inject.Inject

interface PizzaRepository {

    suspend fun getPizzas(): List<Pizza>

}

internal class DefaultPizzaRepository @Inject constructor(

    private val dataSource: PizzaDataSource

) : PizzaRepository {

    override suspend fun getPizzas(): List<Pizza> {

        return dataSource
            .getPizzas()
            .pizzas
            .map { pizzaDto ->

                pizzaDto.toDomain()

            }
    }
}