package com.azhara.jetpackdicodingsubmission3.di

import android.content.Context
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.LocalDataSource
import com.azhara.jetpackdicodingsubmission3.data.source.local.room.MovieDatabase
import com.azhara.jetpackdicodingsubmission3.data.source.remote.RemoteDataSource
import com.azhara.jetpackdicodingsubmission3.utils.AppExecutor
import com.azhara.jetpackdicodingsubmission3.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): MovieRepository {

        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper())
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutor = AppExecutor()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutor)
    }
}