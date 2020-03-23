package com.azhara.jetpackdicodingsubmission3.ui.detail.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.azhara.jetpackdicodingsubmission3.data.source.MovieRepository
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.utils.DataDummy
import com.azhara.jetpackdicodingsubmission3.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest {
    private lateinit var detailTvViewModel: DetailTvViewModel

    private val detailTv = DataDummy.generateDataTv()[0]
    private val tvId = detailTv.id

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvEntity>>

    @Before
    fun setup() {
        detailTvViewModel = DetailTvViewModel(movieRepository)
        detailTvViewModel.setId(tvId)
    }

    @Test
    fun getDetailTv() {
        val dataTv = Resource.success(DataDummy.generateDataTv()[0])
        val tv = MutableLiveData<Resource<TvEntity>>()
        tv.value = dataTv

        `when`(movieRepository.getTvDetail(tvId)).thenReturn(tv)

        detailTvViewModel.getMovieById.observeForever(observer)
        verify(observer).onChanged(dataTv)


    }

}