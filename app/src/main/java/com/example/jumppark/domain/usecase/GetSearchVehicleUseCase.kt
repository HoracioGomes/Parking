package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.Vehicle
import com.example.jumppark.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow

class GetSearchVehicleUseCase(private val repository: VehicleRepository) {
    fun execute(query: String): Flow<List<Vehicle>>{
        return repository.searchVehicle(query)
    }
}