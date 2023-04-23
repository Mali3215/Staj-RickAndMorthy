package com.example.myapplication.locations

import com.example.myapplication.network.NetworkLayer
import com.example.myapplication.network.response.GetLocationsPageResponse

class LocationsRepository {

    suspend fun getLocationsPage(pageIndex: Int): GetLocationsPageResponse?{

        val request = NetworkLayer.apClient.getLocationPage(pageIndex)

        if ( request.failed || !request.isSuccessful){
            return null
        }

        return request.body

    }

}