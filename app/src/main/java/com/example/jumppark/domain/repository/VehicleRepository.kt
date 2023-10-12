package com.example.jumppark.domain.repository

import com.example.jumppark.data.model.Vehicle
import com.example.jumppark.data.util.Resource
import kotlinx.coroutines.flow.Flow


interface VehicleRepository {
    suspend fun saveVehicle(vehicle: Vehicle)
    fun getSavedVehicles(): Flow<List<Vehicle>>
    fun searchVehicle(query: String): Flow<List<Vehicle>>
}