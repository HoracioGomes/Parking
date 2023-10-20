package com.example.jumppark.data.api

import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EstablishmentAPIService {
    @GET("{userId}/establishment/{establishmentId}/sync/manual")
    suspend fun getEstablishmentInformations(
        @Path("establishmentId") establishmentId: String,
        @Path("userId") userId: String
    )
            : Response<EstablishmentResponse>
}