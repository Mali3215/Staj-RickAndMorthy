package com.example.myapplication.domain.mappers

import com.example.myapplication.network.response.GetCharacterByIdResponse
import com.example.myapplication.network.response.GetEpisodeByIdResponse
import com.example.myapplication.domain.models.Character
import com.example.myapplication.domain.models.Episode


object CharacterMapper {


    fun buildFrom(
        response: GetCharacterByIdResponse,
        episodes: List<GetEpisodeByIdResponse>,
    ): Character {
        return Character(
            episodeList = episodes.map {
                EpisodeMapper.buildFrom(it)
            },
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
            created = response.created,



            )
    }



}