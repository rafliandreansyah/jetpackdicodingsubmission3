package com.azhara.jetpackdicodingsubmission3.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.di.Injection
import com.azhara.jetpackdicodingsubmission3.ui.detail.viewmodel.DetailMovieViewModel
import com.azhara.jetpackdicodingsubmission3.ui.detail.viewmodel.DetailTvViewModel
import com.azhara.jetpackdicodingsubmission3.ui.favorite.movie.viewmodel.MovieLocalViewModel
import com.azhara.jetpackdicodingsubmission3.ui.favorite.tvshow.viewmodel.TvLocalViewModel
import com.azhara.jetpackdicodingsubmission3.ui.movie.viewmodel.MoviesViewModel
import com.azhara.jetpackdicodingsubmission3.ui.tvshow.viewmodel.TvShowViewModel


class ViewModelFactory private constructor(private val movieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                return MoviesViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                return DetailMovieViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(MovieLocalViewModel::class.java) -> {
                return MovieLocalViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                return TvShowViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvViewModel::class.java) -> {
                return DetailTvViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(TvLocalViewModel::class.java) -> {
                return TvLocalViewModel(movieRepository) as T
            }
            else -> throw Throwable("View model unknown class: " + modelClass.name)
        }
    }

}