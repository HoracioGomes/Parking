package com.example.jumppark.data.dataSourceImpl

import com.example.jumppark.data.api.EstablishmentAPIService
import com.example.jumppark.data.dataSource.EstablishmentRemoteDataSource
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import retrofit2.Response

class EstablishmentRemoteDataSourceImpl(
    private val establishmentId: String,
    private val service: EstablishmentAPIService
) : EstablishmentRemoteDataSource {
    override suspend fun getEstablishmentInformations(): Response<EstablishmentResponse> {
        return service.getEstablishmentInformations(establishmentId = establishmentId)
    }
}