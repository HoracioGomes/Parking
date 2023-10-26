package com.example.jumppark.presentation.di

import com.example.jumppark.data.dataSource.VoucherLocalDataSource
import com.example.jumppark.data.dataSourceImpl.VoucherLocalDataSourceImpl
import com.example.jumppark.data.db.VoucherDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun providesVoucherLocalDataSource(voucherDAO: VoucherDAO): VoucherLocalDataSource {
        return VoucherLocalDataSourceImpl(voucherDAO)
    }
}