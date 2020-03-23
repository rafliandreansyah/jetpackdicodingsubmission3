package com.azhara.jetpackdicodingsubmission3.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity

@Database(entities = [MoviesEntity::class, TvEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java, "movie.db"
                ).build()
            }
    }
}