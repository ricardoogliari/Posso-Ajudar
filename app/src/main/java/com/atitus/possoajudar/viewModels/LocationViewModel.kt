package com.atitus.possoajudar.viewModels

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atitus.possoajudar.services.ApiState
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.launch

class LocationViewModel(private val fusedLocationClient: FusedLocationProviderClient) : ViewModel() {

    val response: MutableLiveData<ApiState> = MutableLiveData(ApiState.Empty)

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation() {
        response.value = ApiState.Loading
        viewModelScope.launch {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    response.value = ApiState.Success(location)
                }
        }
    }

}