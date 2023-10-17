package com.example.jumppark.presentation.di

import com.example.jumppark.data.api.EstablishmentAPIService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://dev.app.jumpparkapi.com.br/api")
            .build()
    }

    @Singleton
    @Provides
    fun providesEstablishmentAPIService(retrofit: Retrofit): EstablishmentAPIService {
        return retrofit.create(EstablishmentAPIService::class.java)
    }
}