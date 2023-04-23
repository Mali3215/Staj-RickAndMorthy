package com.example.myapplication.network.response

data class GetCharactersPageResponse(
    val info: Info,
    val results: List<GetCharacterByIdResponse> = emptyList()
) {

}