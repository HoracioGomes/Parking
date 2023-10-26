package com.example.jumppark.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.jumppark.data.db.ParkDatabase
import com.example.jumppark.data.db.VoucherDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    private val db_name = "park_database"

    @Singleton
    @Provides
    fun providesParkDatabase(app: Application): ParkDatabase {
        return Room.databaseBuilder(app, ParkDatabase::class.java, db_name)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesVoucherDao(parkDatabase: ParkDatabase): VoucherDAO {
        return parkDatabase.getVoucherDao()
    }
}