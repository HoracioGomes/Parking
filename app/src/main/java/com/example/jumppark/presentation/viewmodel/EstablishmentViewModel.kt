package com.example.jumppark.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.util.Resource
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstablishmentViewModel(
    private val app: Application,
    private val establishmentUseCase: GetStablishmentInformationUseCase
) : AndroidViewModel(app) {
    val establishmentLiveData = MutableLiveData<Resource<EstablishmentResponse>>()

    fun getEstablishmentInformations(establishmentId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            establishmentLiveData.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val requestResult = establishmentUseCase.execute(establishmentId)
                    establishmentLiveData.postValue(requestResult)
                } else {
                    establishmentLiveData.postValue(Resource.Error(message = "Internet is not available!"))
                }
            } catch (e: Exception) {
                establishmentLiveData.postValue(Resource.Error(message = e.message.toString()))
            }
        }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }
}