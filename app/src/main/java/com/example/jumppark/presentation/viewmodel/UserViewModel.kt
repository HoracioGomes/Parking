package com.example.jumppark.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.jumppark.R
import com.example.jumppark.data.dataUtils.Resource
import com.example.jumppark.data.model.UserLogin
import com.example.jumppark.data.model.responses.login.LoginResponse
import com.example.jumppark.data.model.responses.logout.LogoutResponse
import com.example.jumppark.domain.usecase.GetLoginUseCase
import com.example.jumppark.domain.usecase.GetLogoutUseCase
import com.example.jumppark.presentation.presentationUtils.isNetworkAvailable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(
    private val app: Application,
    private val loginUseCase: GetLoginUseCase,
    private val logoutUseCase: GetLogoutUseCase
) : AndroidViewModel(app) {
    val userLoginLiveData: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val userLogoutLiveData: MutableLiveData<Resource<LogoutResponse>> = MutableLiveData()

    fun getLogin(user: UserLogin) {
        viewModelScope.launch(Dispatchers.IO) {
            userLoginLiveData.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val requestResult = loginUseCase.execute(user)
                    userLoginLiveData.postValue(requestResult)
                } else {
                    userLoginLiveData.postValue(Resource.Error(message = app.getString(R.string.internet_is_not_available)))
                }
            } catch (e: Exception) {
                userLoginLiveData.postValue(Resource.Error(message = e.message.toString()))
            }
        }
    }

    fun getLogout(userId: String, establishmentId: String, sessionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userLogoutLiveData.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val requestResult = logoutUseCase.execute(
                        userId = userId,
                        establishmentId = establishmentId,
                        sessionId = sessionId
                    )
                    userLogoutLiveData.postValue(requestResult)
                } else {
                    userLogoutLiveData.postValue(Resource.Error(message = app.getString(R.string.internet_is_not_available)))
                }
            } catch (e: Exception) {
                userLogoutLiveData.postValue(Resource.Error(message = e.message.toString()))
            }
        }
    }
}