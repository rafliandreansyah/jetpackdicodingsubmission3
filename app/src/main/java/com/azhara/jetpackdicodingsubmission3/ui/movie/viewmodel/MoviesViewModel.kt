package com.azhara.jetpackdicodingsubmission3.ui.movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.vo.Resource

class MoviesViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getAllMovies(): LiveData<Resource<PagedList<MoviesEntity>>> {
        val movie = movieRepository.getAllMovies()
        return movie
    }


}