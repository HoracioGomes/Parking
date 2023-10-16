package com.example.jumppark.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EstablishmentAPIServiceTest {
    private lateinit var service: EstablishmentAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EstablishmentAPIService::class.java)
    }

    @Test
    fun getEstablishmentInformations_sendRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("establishment-response.json")
            val responseBody = service.getEstablishmentInformations(establishmentId = "4401").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/establishment/4401/sync/manual")
        }
    }

    @Test
    fun getEstablishments_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("establishment-response.json")
            val responseBody = service.getEstablishmentInformations(establishmentId = "4401").body()
            if (responseBody != null) {
                assertThat(responseBody.data.costCenters[0].costCenterId).isEqualTo(2333)
                assertThat(responseBody.data.paymentMethods.size).isEqualTo(5)
            }
        }
    }


    @After
    fun tearDown() {
        server.shutdown()

    }

    private fun enqueueMockResponse(filename: String) {
        val inputStrem = javaClass.classLoader.getResourceAsStream(filename)
        val buffer = inputStrem.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(buffer.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

}