package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.Vehicle
import com.example.jumppark.domain.repository.VehicleRepository
import kotlinx.coroutines.flow.Flow

class GetParkedVehiclesUseCase(val repository: VehicleRepository) {
    fun execute(): Flow<List<Vehicle>> {
        return repository.getSavedVehicles()
    }
}