package com.example.jumppark.data.repository

import com.example.jumppark.data.dataSource.EstablishmentRemoteDataSource
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.dataUtils.responseToResource
import com.example.jumppark.domain.repository.EstablishmentRepository

class EstablishmentRepositoryImpl(
    private val dataSource: EstablishmentRemoteDataSource
) : EstablishmentRepository {
    override suspend fun getEstablishmentInformations(establishmentId: String, userId: String): Resource<EstablishmentResponse> {
        val response = dataSource.getEstablishmentInformations(establishmentId = establishmentId, userId = userId)
        return responseToResource(response)
    }

}