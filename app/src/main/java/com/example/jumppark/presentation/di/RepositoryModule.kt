package com.example.jumppark.presentation.di

import com.example.jumppark.data.dataSource.EstablishmentRemoteDataSource
import com.example.jumppark.data.repository.EstablishmentRepositoryImpl
import com.example.jumppark.domain.repository.EstablishmentRepository
import com.example.jumppark.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesEstablishmentRepository(establishmentRemoteDataSource: EstablishmentRemoteDataSource)
            : EstablishmentRepository {
        return EstablishmentRepositoryImpl(establishmentRemoteDataSource)
    }

}