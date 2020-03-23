package com.azhara.jetpackdicodingsubmission3.ui.favorite.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity

class MovieLocalViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getAllFavoriteMovie(): LiveData<PagedList<MoviesEntity>> =
        movieRepository.getMovieFavorite()

    fun setMovie(moviesEntity: MoviesEntity) {
        val newState = !moviesEntity.favorite
        movieRepository.setFavoriteMovie(moviesEntity, newState)
    }

}