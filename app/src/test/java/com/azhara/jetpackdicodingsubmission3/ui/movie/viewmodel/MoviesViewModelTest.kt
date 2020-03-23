package com.azhara.jetpackdicodingsubmission3.ui.movie.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.utils.DataDummy
import com.azhara.jetpackdicodingsubmission3.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var moviesViewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MoviesEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<MoviesEntity>

    private val dataDummyMovie = DataDummy.generateDataMovie()

    @Before
    fun setup() {
        moviesViewModel = MoviesViewModel(movieRepository)
    }

    @Test
    fun getAllMovies() {
        val dummyMovie = Resource.success(pagedList)
        `when`(dummyMovie.data?.size).thenReturn(dataDummyMovie.size)
        val movie = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        movie.value = dummyMovie


        `when`(movieRepository.getAllMovies()).thenReturn(movie)
        val moviesEntity = moviesViewModel.getAllMovies().value?.data
        Mockito.verify(movieRepository).getAllMovies()
        assertNotNull(moviesEntity)
        assertEquals(dataDummyMovie.size, moviesEntity?.size)

        moviesViewModel.getAllMovies().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)

    }


}