package com.example.jumppark.presentation.di

import com.example.jumppark.domain.repository.EstablishmentRepository
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetStablishmentInformationUseCase(repository: EstablishmentRepository)
            : GetStablishmentInformationUseCase {
        return GetStablishmentInformationUseCase(repository)
    }
}