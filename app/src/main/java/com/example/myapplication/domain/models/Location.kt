package com.example.myapplication.domain.models

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residentsList: List<String>

)