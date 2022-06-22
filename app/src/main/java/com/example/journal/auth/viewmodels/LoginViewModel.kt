package com.example.journal.auth.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journal.auth.events.AuthEvent
import com.example.journal.data.model.*
import com.example.journal.data.remote.RetrofitBuilder
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    val isFormValid: MutableLiveData<Boolean> = MutableLiveData(true)
    var status: MutableLiveData<Resource<*>> = MutableLiveData()
    private val api = RetrofitBuilder.gossipCentralAPI

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.LoginEvent -> {
                val request = event.request
                if (
                    request.email.isEmpty() ||
                    request.password.isEmpty()
                ) {
                    isFormValid.value = false
                    return
                }
                login(request)
            }
            else -> {}
        }
    }

    private fun login(request: LoginRequest) {
        Log.i("login", "login method called")
        status.value = Resource.loading(data = null);
        viewModelScope.launch(Dispatchers.IO) {
            val threadInfo = Thread.currentThread().name
            Log.i("login", "login running on thread $threadInfo")
            val result: LoginResponse?
            try {
                result = api.login(request)
                if (result.successful) {
                    Log.i("login-success", result.toString())
                    val resource = Resource.success(data = result.data)
                    status.postValue(resource)
                } else {
                    Log.i("login-fail", result.data.toString())
                    val resource = Resource.error(data = null, message = result.data.message)
                    status.postValue(resource)
                }
            } catch (e: HttpException) {
                val resource: Resource<*>
                if (e.code() == 400) {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        e.response()?.errorBody()!!.charStream(),
                        LoginResponse::class.java
                    )
                    Log.i("login-error ", errorResponse.toString())
                    resource = Resource.error(data = null, message = errorResponse.data.message)
                    status.postValue(resource)
                } else {
                    Log.i("login-error", e.toString())
                    Log.i("login-error", e.message())
                    Log.i("login-error", e.response().toString())
                    resource = Resource.error(
                        data = null,
                        message = e.localizedMessage ?: "Looks like something went wrong"
                    )
                    status.postValue(resource)
                }

            } catch (e: Exception) {
                Log.i("login", e.toString())
                val resource = Resource.error(
                    data = null,
                    message = e.localizedMessage ?: "Looks like something went wrong"
                )
                status.postValue(resource)
            }

        }
    }



}