package com.example.myapplication.network

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.myapplication.SimpleMortyApplication
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object NetworkLayer {


    val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val rickAndMorthService: RickAndMortyService by lazy {
        retrofit.create(RickAndMortyService::class.java)

    }
    val apClient = ApiClient(rickAndMorthService)




}