package com.example.jumppark.presentation.di

import android.app.Application
import com.example.jumppark.domain.usecase.GetLoginUseCase
import com.example.jumppark.domain.usecase.GetLogoutUseCase
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.presentation.factory.BaseViewModelFactory
import com.example.jumppark.presentation.factory.EstablishmentViewModelFactory
import com.example.jumppark.presentation.factory.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideEstablishmentViewModelFactory(
        application: Application,
        establisUseCase: GetStablishmentInformationUseCase
    ): EstablishmentViewModelFactory {
        return EstablishmentViewModelFactory(application, establisUseCase)
    }

    @Singleton
    @Provides
    fun provideBaseViewModelFactory(application: Application): BaseViewModelFactory {
        return BaseViewModelFactory(application)
    }

    @Singleton
    @Provides
    fun providesUserViewModelFactory(
        application: Application,
        getLoginUseCase: GetLoginUseCase,
        getLogoutUseCase: GetLogoutUseCase
    ): UserViewModelFactory {
        return UserViewModelFactory(application, getLoginUseCase, getLogoutUseCase)
    }
}