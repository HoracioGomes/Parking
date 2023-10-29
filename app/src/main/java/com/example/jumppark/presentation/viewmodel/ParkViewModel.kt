package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.Voucher
import com.example.jumppark.data.model.responses.establishment.EstablishmentResponse
import com.example.jumppark.data.model.responses.establishment.ItemPrice
import com.example.jumppark.domain.usecase.GetParkedVoucherUseCase
import com.example.jumppark.domain.usecase.GetStablishmentInformationUseCase
import com.example.jumppark.domain.usecase.LaunchEntryUseCase
import com.example.jumppark.presentation.presentationUtils.isNetworkAvailable
import com.example.jumppark.ui.uiUtils.getDifferMinBetweenEntryExit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.util.Date

class ParkViewModel(
    private val app: Application,
    private val getEstablishmentUseCase: GetStablishmentInformationUseCase,
    private val launchEntryUseCase: LaunchEntryUseCase,
    private val getParkedVoucherUseCase: GetParkedVoucherUseCase
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

    fun getVouchers() = liveData {
        getParkedVoucherUseCase.execute().collect(FlowCollector { list ->
            emit(list)
        })
    }

    fun getTolerance(): Int {
        return _establishmentLiveData.value?.data?.data?.prices?.get(0)?.tolerance ?: 0
    }

    fun getmaximumPeriod(): Int {
        return _establishmentLiveData.value?.data?.data?.prices?.get(0)?.maximumPeriod ?: 0
    }

    fun getmaximumValue(): String {
        return _establishmentLiveData.value?.data?.data?.prices?.get(0)?.maximumValue ?: ""
    }

    fun getPrices(): List<ItemPrice>? {
        return _establishmentLiveData.value?.data?.data?.prices?.get(0)?.items
    }

    private fun calcLowerValuePosToleranceToPayableValue(entryDate: Date?): Double {
        val priceItems = getPrices()
        val consummatedMinutes = getDifferMinBetweenEntryExit(entryDate)
        var payableValue = 0.0

        if (consummatedMinutes > getTolerance()) {
            if (priceItems != null) {
                payableValue = priceItems[0].price.toDouble()
                for (itemPrice in priceItems) {
                    payableValue =
                        if (itemPrice.price.toDouble() < payableValue) itemPrice.price.toDouble() else payableValue
                }
            }
        }
        return payableValue
    }


    private fun calcPayableTableValue(entryDate: Date?): Double {
        val priceItems = getPrices()
        val consummatedMinutes = getDifferMinBetweenEntryExit(entryDate)
        var payableValue = calcLowerValuePosToleranceToPayableValue(entryDate)
        if (priceItems != null) {
            for (itemPrice in priceItems) {
                if (consummatedMinutes >= (itemPrice.period + getTolerance())) {
                    payableValue =
                        if (itemPrice.price.toDouble() > payableValue) itemPrice.price.toDouble() else payableValue
                }
            }
        }
        return payableValue
    }

    fun calcSinceAndLimitsToFinalPayableValue(entryDate: Date?): Double {
        val initialPayableValue = calcPayableTableValue(entryDate)
        val priceItems = getPrices()
        val consummatedMinutes = getDifferMinBetweenEntryExit(entryDate)
        if (priceItems != null) {
            for (itemPrice in priceItems) {
                if (itemPrice.since in 1..consummatedMinutes) {
                    val posConsummatedMinutes = consummatedMinutes - itemPrice.since
                    val posConsummatedHours = (posConsummatedMinutes / itemPrice.period)
                    val sinceValue = posConsummatedHours * (itemPrice.price.toDouble())
                    return initialPayableValue.plus(sinceValue)
                }
            }
        }
        return if (consummatedMinutes <= getmaximumPeriod() && initialPayableValue > getmaximumValue().toDouble()) {
            getmaximumValue().toDouble()
        } else {
            initialPayableValue
        }
    }


}