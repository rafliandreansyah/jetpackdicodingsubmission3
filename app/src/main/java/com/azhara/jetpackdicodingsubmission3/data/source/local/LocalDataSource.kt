package com.azhara.jetpackdicodingsubmission3.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val movieDao: MovieDao) {

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            instance ?: synchronized(this) {
                instance ?: LocalDataSource(movieDao)
            }
    }

    fun getAllMovie(): DataSource.Factory<Int, MoviesEntity> = movieDao.getAllMovie()

    fun InsertAllMovie(movies: List<MoviesEntity>) = movieDao.insertAllMovie(movies)

    fun insertMovie(movie: MoviesEntity) = movieDao.insertMovieDetail(movie)

    fun getMovieById(id: String): LiveData<MoviesEntity> = movieDao.getMovieById(id)

    fun updateMovie(movie: MoviesEntity, newState: Boolean) {
        movie.favorite = newState
        movieDao.updateMovie(movie)
    }

    fun getFavoriteMovie(): DataSource.Factory<Int, MoviesEntity> = movieDao.getAllMovieFavorite()

    fun insertAllTv(tvList: List<TvEntity>) = movieDao.insertAllTv(tvList)

    fun insertTvDetail(tv: TvEntity) = movieDao.insertTvDetail(tv)

    fun getAllTv(): DataSource.Factory<Int, TvEntity> = movieDao.getAllTv()

    fun getAllTvFavorite(): DataSource.Factory<Int, TvEntity> = movieDao.getAllTvFavorite()

    fun getTvById(id: String): LiveData<TvEntity> = movieDao.getTvById(id)

    fun updateTv(tvEntity: TvEntity, newState: Boolean) {
        tvEntity.favorite = newState
        movieDao.updateTv(tvEntity)
    }

}