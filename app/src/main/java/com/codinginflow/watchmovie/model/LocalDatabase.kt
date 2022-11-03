package com.codinginflow.watchmovie.model

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codinginflow.watchmovie.data.IntegerListTypeConverter
import com.codinginflow.watchmovie.data.Movie


@Database(entities = [Movie::class], version = 1)
@TypeConverters(IntegerListTypeConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    companion object {
        private val lock = Any()

        private const val DB_NAME = "movie_database"
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(application: Application): LocalDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(application, LocalDatabase::class.java, DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

}