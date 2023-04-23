package com.example.myapplication.network.response

class GetLocationsPageResponse (
    val info: Info,
    val results: List<GetLocationByIdResponse> = emptyList()
)