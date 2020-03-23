package com.azhara.jetpackdicodingsubmission3.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity

@Dao
interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllMovie(movies: List<MoviesEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieDetail(movies: MoviesEntity)

    @Update
    fun updateMovie(moviesEntity: MoviesEntity)

    @Delete
    fun deleteMovie(moviesEntity: MoviesEntity)

    @Query("SELECT * FROM movie")
    fun getAllMovie(): DataSource.Factory<Int, MoviesEntity>

    @Query("SELECT * FROM movie WHERE favorite = 1")
    fun getAllMovieFavorite(): DataSource.Factory<Int, MoviesEntity>

    @Transaction
    @Query("SELECT * FROM movie WHERE id = :movieId")
    fun getMovieById(movieId: String): LiveData<MoviesEntity>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllTv(tvList: List<TvEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvDetail(tv: TvEntity)

    @Update
    fun updateTv(tvEntity: TvEntity)

    @Delete
    fun deleteTv(tvEntity: TvEntity)

    @Query("SELECT * FROM tv_show")
    fun getAllTv(): DataSource.Factory<Int, TvEntity>

    @Query("SELECT * FROM tv_show WHERE favorite = 1")
    fun getAllTvFavorite(): DataSource.Factory<Int, TvEntity>

    @Transaction
    @Query("SELECT * FROM tv_show WHERE id = :tvId")
    fun getTvById(tvId: String): LiveData<TvEntity>
}