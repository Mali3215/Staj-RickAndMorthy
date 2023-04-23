package com.example.myapplication.network.response

class GetLocationByIdResponse (

    val type: String,
    val residents: List<String>,
    val created: String,
    val dimension: String,
    val id: Int = 0,
    val name: String,
    val url: String

){

}