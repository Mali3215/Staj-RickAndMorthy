package com.example.myapplication.locations

import androidx.paging.DataSource
import com.example.myapplication.network.response.GetLocationByIdResponse
import kotlinx.coroutines.CoroutineScope

class LocationsDataSourceFactory(
    private val coroutineScope: CoroutineScope,
    private val repository: LocationsRepository
): DataSource.Factory<Int, GetLocationByIdResponse>(){
    override fun create(): DataSource<Int, GetLocationByIdResponse> {


        return LocationDataSource(coroutineScope,repository)
    }

}