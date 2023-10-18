package com.example.jumppark.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jumppark.R
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.presentation.presentationUtils.isNetworkAvailable
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
                    establishmentLiveData.postValue(Resource.Error(message = app.getString(R.string.internet_is_not_available)))
                }
            } catch (e: Exception) {
                establishmentLiveData.postValue(Resource.Error(message = e.message.toString()))
            }
        }
}