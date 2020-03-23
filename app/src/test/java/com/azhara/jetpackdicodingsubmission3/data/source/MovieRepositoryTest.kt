package com.azhara.jetpackdicodingsubmission3.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.azhara.jetpackdicodingsubmission3.data.source.local.LocalDataSource
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.data.source.remote.RemoteDataSource
import com.azhara.jetpackdicodingsubmission3.utils.AppExecutor
import com.azhara.jetpackdicodingsubmission3.utils.DataDummy
import com.azhara.jetpackdicodingsubmission3.utils.LiveDataTestUtil
import com.azhara.jetpackdicodingsubmission3.utils.PagedListUtil
import com.azhara.jetpackdicodingsubmission3.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remoteDataSource = mock(RemoteDataSource::class.java)
    private val localDataSource = mock(LocalDataSource::class.java)
    private val appExecutor = mock(AppExecutor::class.java)

    private val movieRepository =
        FakeMovieRepository(remoteDataSource, localDataSource, appExecutor)

    private val movieResponse = DataDummy.generateDataMovie()
    private val movieId = movieResponse[0].id

    private val tvResponse = DataDummy.generateDataTv()
    private val tvId = tvResponse[0].id

    @Test
    fun getAllMovies() {
        val dataSource =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(localDataSource.getAllMovie()).thenReturn(dataSource)
        movieRepository.getAllMovies()

        val moviesEntity =
            Resource.success(PagedListUtil.mockPageList(DataDummy.generateDataMovie()))
        verify(localDataSource).getAllMovie()
        assertNotNull(moviesEntity)
        assertEquals(movieResponse.size.toLong(), moviesEntity.data?.size?.toLong())
    }

    @Test
    fun getAllTv() {
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(localDataSource.getAllTv()).thenReturn(dataSource)
        movieRepository.getAllTv()

        val tvEntity = Resource.success(PagedListUtil.mockPageList(DataDummy.generateDataTv()))
        verify(localDataSource).getAllTv()
        assertNotNull(tvEntity)
        assertEquals(tvResponse.size.toLong(), tvEntity.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail() {
        val dummyEntity = MutableLiveData<MoviesEntity>()
        dummyEntity.value = movieResponse[0]

        `when`(localDataSource.getMovieById(movieId)).thenReturn(dummyEntity)
        val resultDetailMovie = LiveDataTestUtil.getValue(movieRepository.getMovieDetail(movieId))
        verify(localDataSource).getMovieById(movieId)
        assertNotNull(resultDetailMovie.data)
        assertNotNull(resultDetailMovie.data?.id)
        assertEquals(movieResponse[0].id, resultDetailMovie.data?.id)
    }

    @Test
    fun getTvDetail() {
        val dummyEntity = MutableLiveData<TvEntity>()
        dummyEntity.value = tvResponse[0]

        `when`(localDataSource.getTvById(tvId)).thenReturn(dummyEntity)
        val resultDetailTv = LiveDataTestUtil.getValue(movieRepository.getTvDetail(tvId))
        verify(localDataSource).getTvById(tvId)
        assertNotNull(resultDetailTv.data)
        assertNotNull(resultDetailTv.data?.id)
        assertEquals(tvResponse[0].id, resultDetailTv.data?.id)
    }

    @Test
    fun getMovieFavorite() {
        val dataSource =
            mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(localDataSource.getFavoriteMovie()).thenReturn(dataSource)
        movieRepository.getMovieFavorite()

        val moviesEntity =
            Resource.success(PagedListUtil.mockPageList(DataDummy.generateDataMovie()))
        verify(localDataSource).getFavoriteMovie()
        assertNotNull(moviesEntity)
        assertEquals(movieResponse.size.toLong(), moviesEntity.data?.size?.toLong())

    }

    @Test
    fun getTvFavorite() {
        val dataSource = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(localDataSource.getAllTvFavorite()).thenReturn(dataSource)
        movieRepository.getTvFavorite()

        val tvEntity = Resource.success(PagedListUtil.mockPageList(DataDummy.generateDataTv()))
        verify(localDataSource).getAllTvFavorite()
        assertNotNull(tvEntity)
        assertEquals(movieResponse.size.toLong(), tvEntity.data?.size?.toLong())
    }

}