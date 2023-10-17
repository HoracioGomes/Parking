package com.example.jumppark.data.util

import retrofit2.Response

fun <T> responseToResource(response: Response<T>): Resource<T> {
    if (response.isSuccessful) {
        response.body()?.let { result ->
            return Resource.Success(result)
        }
    }

    return Resource.Error(message = response.message())
}