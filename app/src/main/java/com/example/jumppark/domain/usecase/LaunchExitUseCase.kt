package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.Vehicle
import com.example.jumppark.domain.repository.VehicleRepository

class LaunchExitUseCase(val repository: VehicleRepository) {
    suspend fun execute(vehicle: Vehicle) = repository.saveVehicle(vehicle)
}