package com.azhara.jetpackdicodingsubmission3.ui.tvshow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.vo.Resource

class TvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getAllTvShow(): LiveData<Resource<PagedList<TvEntity>>> = movieRepository.getAllTv()

}