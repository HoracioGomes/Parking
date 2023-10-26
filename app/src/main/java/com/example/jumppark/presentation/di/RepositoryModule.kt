package com.example.jumppark.presentation.di

import com.example.jumppark.data.dataSource.EstablishmentRemoteDataSource
import com.example.jumppark.data.dataSource.UserRemoteDataSource
import com.example.jumppark.data.dataSource.VoucherLocalDataSource
import com.example.jumppark.data.repository.EstablishmentRepositoryImpl
import com.example.jumppark.data.repository.UserRepositoryImpl
import com.example.jumppark.data.repository.VoucherRepositoryImpl
import com.example.jumppark.domain.repository.EstablishmentRepository
import com.example.jumppark.domain.repository.UserRepository
import com.example.jumppark.domain.repository.VoucherRepository
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

    @Singleton
    @Provides
    fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository {
        return UserRepositoryImpl(userRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesVoucherRepository(voucherLocalDataSource: VoucherLocalDataSource): VoucherRepository {
        return VoucherRepositoryImpl(voucherLocalDataSource)
    }

}