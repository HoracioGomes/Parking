package com.example.jumppark.presentation.di

import com.example.jumppark.data.api.EstablishmentAPIService
import com.example.jumppark.data.api.UserAPIService
import com.example.jumppark.data.dataSource.EstablishmentRemoteDataSource
import com.example.jumppark.data.dataSource.UserRemoteDataSource
import com.example.jumppark.data.dataSourceImpl.EstablishmentRemoteDataSourceImpl
import com.example.jumppark.data.dataSourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideEstablishmentDataSource(establishmentAPIService: EstablishmentAPIService)
            : EstablishmentRemoteDataSource {
        return EstablishmentRemoteDataSourceImpl(establishmentAPIService)
    }

    @Singleton
    @Provides
    fun provideUserDataSource(userAPIService: UserAPIService): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(userAPIService)
    }
}