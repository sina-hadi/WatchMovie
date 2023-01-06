package com.codinginflow.watchmovie.model

import androidx.room.*
import com.codinginflow.watchmovie.data.Movie
import io.reactivex.Observable

@Dao
interface MovieDao {

    @get:Query("SELECT * FROM movie_table")
    val all: Observable<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Query("DELETE FROM movie_table WHERE id = :id")
    fun delete(id: Int?)

    @Update
    fun update(movie: Movie)

}