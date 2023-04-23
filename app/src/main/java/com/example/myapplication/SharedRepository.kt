package com.example.myapplication

import com.example.myapplication.domain.mappers.CharacterMapper
import com.example.myapplication.domain.models.Character
import com.example.myapplication.network.NetworkLayer
import com.example.myapplication.network.response.GetCharacterByIdResponse
import com.example.myapplication.network.response.GetEpisodeByIdResponse


class SharedRepository {

    suspend fun getCharacterById(characterId: Int): Character?{

        val request = NetworkLayer.apClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessful){
            return null
        }


        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)


        return CharacterMapper.buildFrom(
            response = request.body,
            episodes = networkEpisodes,
        )

    }


    private suspend fun getEpisodesFromCharacterResponse(
        characterResponse: GetCharacterByIdResponse
    ): List<GetEpisodeByIdResponse>{

        val episodeRange = characterResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/")+1)
        }.toString()
        val request = NetworkLayer.apClient.getEpisodeRange(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }


        return request.body

    }



}