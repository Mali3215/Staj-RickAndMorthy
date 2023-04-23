package com.example.myapplication.locations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.models.Location
import kotlinx.coroutines.launch

class LocationsSharedViewModel: ViewModel() {

    private val repository = LocationsSharedRepository()

    private val _locationByIdLiveData = MutableLiveData<Location>()

    val locationByIdLiveData: LiveData<Location> = _locationByIdLiveData

    fun refreshLocation(locationId: Int){
        viewModelScope.launch {
            val response = repository.getLocationsById(locationId)
            _locationByIdLiveData.postValue(response)
        }
    }

}