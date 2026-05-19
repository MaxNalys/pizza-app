package com.dev.data.repository.di


import com.dev.data.repository.DefaultPizzaRepository
import com.dev.data.repository.PizzaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindPizzaRepository(
        repository: DefaultPizzaRepository
    ): PizzaRepository
}