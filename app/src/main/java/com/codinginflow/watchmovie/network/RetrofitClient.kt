package com.codinginflow.watchmovie.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory.*

object RetrofitClient {

    const val API_KEY = "6fc85b216e003db2b141a5c17bb0c5db"
    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val TMDB_IMAGEURL = "https://image.tmdb.org/t/p/w500/"

    val moviesApi: RetrofitInterface = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RetrofitInterface::class.java)


}