package com.dev.data.remote.di

import com.dev.data.remote.PizzaApiService
import com.dev.network.RetrofitFactory

import dagger.Module

import dagger.Provides

import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module

@InstallIn(SingletonComponent::class)

object PizzaApiModule {

    @Provides

    @Singleton

    fun providePizzaApiService(

        factory: RetrofitFactory

    ): PizzaApiService {

        return factory

            .create("https://oursongapp.com/")

            .create(PizzaApiService::class.java)

    }

}