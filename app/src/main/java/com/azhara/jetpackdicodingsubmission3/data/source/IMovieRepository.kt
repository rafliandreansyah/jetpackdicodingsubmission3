package com.azhara.jetpackdicodingsubmission3.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.vo.Resource

interface IMovieRepository {

    fun getAllMovies(): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getMovieFavorite(): LiveData<PagedList<MoviesEntity>>

    fun getMovieDetail(movieId: String): LiveData<Resource<MoviesEntity>>

    fun setFavoriteMovie(movie: MoviesEntity, state: Boolean)

    fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>>

    fun getTvFavorite(): LiveData<PagedList<TvEntity>>

    fun getTvDetail(tvId: String): LiveData<Resource<TvEntity>>

    fun setFavoriteTv(tvEntity: TvEntity, state: Boolean)

}