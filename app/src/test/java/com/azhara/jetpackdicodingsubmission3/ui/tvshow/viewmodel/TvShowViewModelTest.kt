package com.azhara.jetpackdicodingsubmission3.ui.tvshow.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
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
class TvShowViewModelTest {

    private lateinit var tvShowViewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<TvEntity>

    private val dataDummyTv = DataDummy.generateDataTv()

    @Before
    fun setup() {
        tvShowViewModel = TvShowViewModel(movieRepository)
    }

    @Test
    fun getAllTvShow() {
        val dummyTv = Resource.success(pagedList)
        `when`(dummyTv.data?.size).thenReturn(dataDummyTv.size)
        val tv = MutableLiveData<Resource<PagedList<TvEntity>>>()
        tv.value = dummyTv

        `when`(movieRepository.getAllTv()).thenReturn(tv)
        val tvEntity = tvShowViewModel.getAllTvShow().value?.data
        Mockito.verify(movieRepository).getAllTv()
        assertNotNull(tvEntity)
        assertEquals(dataDummyTv.size, tvEntity?.size)

        tvShowViewModel.getAllTvShow().observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTv)
    }

}