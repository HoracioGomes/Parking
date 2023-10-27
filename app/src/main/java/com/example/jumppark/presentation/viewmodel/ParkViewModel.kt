package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.Voucher
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.domain.usecase.LaunchEntryUseCase
import com.example.jumppark.presentation.presentationUtils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParkViewModel(
    private val app: Application,
    private val getEstablishmentUseCase: GetStablishmentInformationUseCase,
    private val launchEntryUseCase: LaunchEntryUseCase
) : AndroidViewModel(app) {
    private val _establishmentLiveData = MutableLiveData<Resource<EstablishmentResponse>>()
    private val establishmentLiveData: LiveData<Resource<EstablishmentResponse>> get() = _establishmentLiveData

    fun fetchEstablishmentInformations(establishmentId: String, userId: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isNetworkAvailable(app)) {
                    _establishmentLiveData.postValue(Resource.Loading())
                    val requestResult = getEstablishmentUseCase.execute(
                        establishmentId = establishmentId,
                        userId = userId
                    )
                    _establishmentLiveData.postValue(requestResult)
                } else {
                    _establishmentLiveData.postValue(
                        Resource.Error(
                            data = null,
                            message = app.getString(R.string.internet_is_not_available)
                        )
                    )
                }
            } catch (e: Exception) {
                _establishmentLiveData.postValue(
                    Resource.Error(
                        data = null,
                        message = e.message.toString()
                    )
                )
            }
        }

    fun getEstablishmentData(): LiveData<Resource<EstablishmentResponse>> {
        return establishmentLiveData
    }

    fun saveVoucher(voucher: Voucher) = viewModelScope.launch {
        launchEntryUseCase.execute(voucher)
    }
}