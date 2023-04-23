package com.example.myapplication.domain.mappers

import com.example.myapplication.network.response.GetLocationByIdResponse
import com.example.myapplication.domain.models.Location

object LocationMapper {

    fun buildFrom(
        response: GetLocationByIdResponse,

        ): Location {
        return Location(
            id = response.id,
            name = response.name,
            type = response.type,
            dimension = response.dimension,
            residentsList = response.residents
        )
    }


}