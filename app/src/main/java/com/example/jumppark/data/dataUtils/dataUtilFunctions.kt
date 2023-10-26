package com.example.jumppark.data.dataUtils

import retrofit2.Response

fun <T> responseToResource(response: Response<T>): Resource<T> {
    if (response.isSuccessful) {
        response.body()?.let { result ->
            return Resource.Success(result)
        }
    } else if (response.raw().code == 400) {
        return Resource.Error(data = null, message = "invalid credential!")

    } else if (response.raw().code == 422) {

        return Resource.Error(data = null, message = "incorrect format!")
    }

    return Resource.Error(data = null, message = "Something went wrong!")

}