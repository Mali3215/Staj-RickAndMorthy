package com.example.myapplication.network

import com.example.myapplication.network.response.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RickAndMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacterById(
        @Path("character-id") characterId: Int
    ): Response<GetCharacterByIdResponse>
    @GET("character/{character-range}")
    suspend fun getCharacterRange(
        @Path("character-range") characterRange: String
    ): Response<List<GetCharacterByIdResponse>>

    @GET("character")
    suspend fun getCharactersPage(
        @Query("page") pageIndex: Int
    ): Response<GetCharactersPageResponse>

    @GET("episode/{episode-id}")
    suspend fun getEpisodeById(
        @Path("episode-id") episodeId: Int
    ): Response<GetEpisodeByIdResponse>

    @GET("episode/{episode-range}")
    suspend fun getEpisodeRange(
        @Path("episode-range") episodeRange: String
    ): Response<List<GetEpisodeByIdResponse>>

    @GET("location/{location-id}")
    suspend fun getLocationById(
        @Path("location-id") locationId: Int
    ): Response<GetLocationByIdResponse>

    @GET("location")
    suspend fun getLocationsPage(
        @Query("page") pageIndex: Int
    ): Response<GetLocationsPageResponse>

    @GET("location/{location-range}")
    suspend fun getLocationRange(
        @Path("location-range") locationRange: String
    ): Response<List<GetLocationByIdResponse>>


}