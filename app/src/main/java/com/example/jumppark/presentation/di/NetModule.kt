package com.example.jumppark.presentation.di

import android.content.SharedPreferences
import com.example.jumppark.data.api.EstablishmentAPIService
import com.example.jumppark.data.api.UserAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {


    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            )
            .baseUrl("https://dev.app.jumpparkapi.com.br/api/")
            .build()
    }

    @Singleton
    @Provides
    fun providesEstablishmentAPIService(retrofit: Retrofit): EstablishmentAPIService {
        return retrofit.create(EstablishmentAPIService::class.java)
    }

    @Singleton
    @Provides
    fun providesUserAPIService(retrofit: Retrofit): UserAPIService {
        return retrofit.create(UserAPIService::class.java)
    }
}