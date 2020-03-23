package com.azhara.jetpackdicodingsubmission3.ui.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.utils.DataDummy
import com.azhara.jetpackdicodingsubmission3.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var detailMovieViewModel: DetailMovieViewModel

    private val detailMovie = DataDummy.generateDataMovie()[0]
    private val movieId = detailMovie.id

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<MoviesEntity>>

    @Before
    fun setup() {
        detailMovieViewModel = DetailMovieViewModel(movieRepository)
        detailMovieViewModel.setData(movieId)
    }

    @Test
    fun getDetailMovie() {
        val dataMovie = Resource.success(DataDummy.generateDataMovie()[0])
        val movie = MutableLiveData<Resource<MoviesEntity>>()
        movie.value = dataMovie
        `when`(movieRepository.getMovieDetail(movieId)).thenReturn(movie)

        detailMovieViewModel.getDetailMovie.observeForever(observer)
        Mockito.verify(observer).onChanged(dataMovie)

    }

}