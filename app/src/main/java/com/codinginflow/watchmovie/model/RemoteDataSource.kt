package com.codinginflow.watchmovie.model

import com.codinginflow.watchmovie.Constant.Companion.API_KEY
import com.codinginflow.watchmovie.data.TmdbResponse
import com.codinginflow.watchmovie.network.RetrofitClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class RemoteDataSource {

    fun searchResultsObservable(query: String): Observable<TmdbResponse> {
        return RetrofitClient.moviesApi
            .searchMovie(API_KEY, query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}