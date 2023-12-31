package com.example.jumppark.presentation.di

import com.example.jumppark.domain.repository.EstablishmentRepository
import com.example.jumppark.domain.repository.UserRepository
import com.example.jumppark.domain.repository.VoucherRepository
import com.example.jumppark.domain.usecase.GetLoginUseCase
import com.example.jumppark.domain.usecase.GetLogoutUseCase
import com.example.jumppark.domain.usecase.GetSavedVoucherUseCase
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.domain.usecase.LaunchEntryUseCase
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

    @Singleton
    @Provides
    fun provideGetLoginUseCase(repository: UserRepository): GetLoginUseCase {
        return GetLoginUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetLogoutUseCase(repository: UserRepository): GetLogoutUseCase {
        return GetLogoutUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesLauchEntryUseCase(repository: VoucherRepository): LaunchEntryUseCase {
        return LaunchEntryUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetParkedVouchersUseCase(repository: VoucherRepository): GetSavedVoucherUseCase {
        return GetSavedVoucherUseCase(repository)
    }
}