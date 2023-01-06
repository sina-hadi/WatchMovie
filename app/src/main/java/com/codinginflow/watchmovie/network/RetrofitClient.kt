package com.codinginflow.watchmovie.network

import com.codinginflow.watchmovie.Constant.Companion.TMDB_BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory.*

object RetrofitClient {

    val moviesApi: RetrofitInterface = Retrofit.Builder()
        .baseUrl(TMDB_BASE_URL)
        .addConverterFactory(create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(RetrofitInterface::class.java)

}