package com.azhara.jetpackdicodingsubmission3.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.vo.Resource

class DetailTvViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val id = MutableLiveData<String>()

    fun setId(tvId: String) {
        this.id.value = tvId
    }

    var getMovieById: LiveData<Resource<TvEntity>> = Transformations.switchMap(id) { movieId ->
        movieRepository.getTvDetail(movieId)
    }

    fun setFavorite() {
        val resourceTv = getMovieById.value

        if (resourceTv != null) {
            val tvData = resourceTv.data
            val state = !tvData?.favorite!!
            movieRepository.setFavoriteTv(tvData, state)

        }
    }

}