package com.example.jumppark.domain.usecase

import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.domain.repository.EstablishmentRepository


class GetStablishmentInformationUseCase(private val repository: EstablishmentRepository) {
    suspend fun execute(establishmentId: String, userId: String): Resource<EstablishmentResponse> {
        return repository.getEstablishmentInformations(establishmentId = establishmentId, userId = userId)
    }

}