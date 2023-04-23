package com.example.myapplication.locations

import com.example.myapplication.network.NetworkLayer
import com.example.myapplication.domain.models.Location
import com.example.myapplication.network.response.GetLocationByIdResponse


class LocationsSharedRepository {

    suspend fun getLocationsById(locationsId: Int): Location? {
        val request = NetworkLayer.apClient.getLocationById(locationsId)

        if (request.failed || !request.isSuccessful){
            return null
        }
        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)


        return Location(
            name = request.body.name,
            id = request.body.id,
            type = request.body.type,
            dimension = request.body.dimension,
            residentsList = request.body.residents
        )
    }

    private suspend fun getEpisodesFromCharacterResponse(
        characterResponse: GetLocationByIdResponse
    ): List<GetLocationByIdResponse>{

        val episodeRange = characterResponse.residents.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apClient.getLocationRange(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }


        return request.body

    }




















/*
    suspend fun getLocationsById(locationsId: Int): Location? {
        val request = NetworkLayer.apClient.getLocationById(locationsId)

        if (request.failed || !request.isSuccessful){
            return null
        }


        return Location(
            name = request.body.name,
            id = request.body.id,
            type = request.body.type,
            dimension = request.body.dimension,
            residents = request.body.residents
        )

    }*/


}