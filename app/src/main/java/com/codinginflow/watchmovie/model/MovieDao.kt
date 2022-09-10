package com.codinginflow.watchmovie.model

import androidx.room.*
import io.reactivex.Observable

@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie_table")
    val all: Observable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("DELETE FROM movie_table WHERE id = :id")
    fun delete(id: Int?)

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Update
    fun update(movie: Movie)

}