package com.azhara.jetpackdicodingsubmission3.ui.favorite.tvshow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity

class TvLocalViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getAllTvFavorite(): LiveData<PagedList<TvEntity>> = movieRepository.getTvFavorite()

}