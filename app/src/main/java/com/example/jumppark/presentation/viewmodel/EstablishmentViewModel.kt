package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.presentation.presentationUtils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstablishmentViewModel(
    private val app: Application,
    private val establishmentUseCase: GetStablishmentInformationUseCase
) : AndroidViewModel(app) {
    private val _establishmentLiveData = MutableLiveData<Resource<EstablishmentResponse>>()
    private val establishmentLiveData: LiveData<Resource<EstablishmentResponse>> get() = _establishmentLiveData

    fun fetchEstablishmentInformations(establishmentId: String, userId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isNetworkAvailable(app)) {
                    _establishmentLiveData.postValue(Resource.Loading())
                    val requestResult = establishmentUseCase.execute(establishmentId = establishmentId, userId = userId)
                    _establishmentLiveData.postValue(requestResult)
                } else {
                    _establishmentLiveData.postValue(Resource.Error(message = app.getString(R.string.internet_is_not_available)))
                }
            } catch (e: Exception) {
                _establishmentLiveData.postValue(Resource.Error(message = e.message.toString()))
            }
        }

    fun getEstablishmentData(): LiveData<Resource<EstablishmentResponse>> {
        return establishmentLiveData
    }
}