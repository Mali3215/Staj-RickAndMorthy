package com.example.myapplication.domain.mappers

import com.example.myapplication.network.response.GetCharacterByIdResponse
import com.example.myapplication.domain.models.Character

object ResidentsMapper {


    fun buildFrom(
        response: GetCharacterByIdResponse
    ): Character {
        return Character(
            episodeList = emptyList(),
            gender = response.gender,
            id = response.id,
            image = response.image,
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            created = response.created

            )
    }


}