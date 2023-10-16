package com.example.jumppark.data.repository

import com.example.jumppark.data.dataSource.EstablishmentRemoteDataSource
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.util.Resource
import com.example.jumppark.data.util.responseToResource
import com.example.jumppark.domain.repository.EstablishmentRepository

class EstablishmentRepositoryImpl(
    private val dataSource: EstablishmentRemoteDataSource
) : EstablishmentRepository {
    override suspend fun getEstablishmentInformations(): Resource<EstablishmentResponse> {
        val response = dataSource.getEstablishmentInformations()
        return responseToResource(response)
    }

}