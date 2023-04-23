package com.example.myapplication.characters

import com.example.myapplication.network.response.GetCharactersPageResponse
import com.example.myapplication.network.NetworkLayer


class CharactersRepository {

    suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse?{

        val request = NetworkLayer.apClient.getCharactersPage(pageIndex)

        if ( request.failed || !request.isSuccessful){
            return null
        }

        return request.body

    }


}