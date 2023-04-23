package com.example.myapplication.network

import com.example.myapplication.*
import com.example.myapplication.network.response.*
import retrofit2.Response


class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
    }
    suspend fun getCharacterRange(characterRange: String): SimpleResponse<List<GetCharacterByIdResponse>> {
        return safeApiCall { rickAndMortyService.getCharacterRange(characterRange) }
    }
    suspend fun getCharactersPage(pageIndex: Int): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall {
            rickAndMortyService.getCharactersPage(pageIndex)
        }
    }

    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse> {
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>> {
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    suspend fun getLocationById(locationId: Int): SimpleResponse<GetLocationByIdResponse> {
        return safeApiCall { rickAndMortyService.getLocationById(locationId) }
    }
    suspend fun getLocationPage(pageIndex: Int): SimpleResponse<GetLocationsPageResponse> {
        return safeApiCall {
            rickAndMortyService.getLocationsPage(pageIndex)
        }
    }
    suspend fun getLocationRange(locationRange: String): SimpleResponse<List<GetLocationByIdResponse>> {
        return safeApiCall { rickAndMortyService.getLocationRange(locationRange) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {

        return try {
            SimpleResponse.success(apiCall.invoke())
        }catch (e: Exception){
            SimpleResponse.failure(e)
        }

    }


}