package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.util.Resource
import com.example.jumppark.domain.repository.EstablishmentRepository


class GetStablishmentInformationUseCase(private val repository: EstablishmentRepository) {
    suspend fun execute(): Resource<EstablishmentResponse> {
        return repository.getEstablishmentInformations()
    }

}