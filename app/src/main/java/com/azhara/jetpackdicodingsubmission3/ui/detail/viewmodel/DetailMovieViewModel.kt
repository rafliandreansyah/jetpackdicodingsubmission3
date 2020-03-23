package com.azhara.jetpackdicodingsubmission3.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.vo.Resource

class DetailMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    var idMovie = MutableLiveData<String>()

    fun setData(id: String) {
        this.idMovie.value = id
    }

    var getDetailMovie: LiveData<Resource<MoviesEntity>> =
        Transformations.switchMap(idMovie) { movieId ->
            movieRepository.getMovieDetail(movieId)
        }


    fun setFavorite() {
        val resourceMovie = getDetailMovie.value

        if (resourceMovie != null) {
            val movieData = resourceMovie.data
            val newState = !movieData?.favorite!!
            movieRepository.setFavoriteMovie(movieData, newState)
        }
    }

}