package com.azhara.jetpackdicodingsubmission3.data.source.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.azhara.jetpackdicodingsubmission3.data.source.remote.response.MovieResponse
import com.azhara.jetpackdicodingsubmission3.data.source.remote.response.TvResponse
import com.azhara.jetpackdicodingsubmission3.utils.IdlingResource
import com.azhara.jetpackdicodingsubmission3.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(jsonHelper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(jsonHelper)
            }

    }

    fun getAllMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        val result = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        IdlingResource.increment()
        jsonHelper.getMovies(object : JsonHelper.LoadMovieCallBack {
            override fun loadMovie(movie: ArrayList<MovieResponse>) {
                result.value = ApiResponse.success(movie)
                IdlingResource.decrement()
            }
        })
        return result
    }

    fun getDetailMovie(idMovie: String): LiveData<ApiResponse<MovieResponse>> {
        val result = MutableLiveData<ApiResponse<MovieResponse>>()
        IdlingResource.increment()
        jsonHelper.getDetailMovie(idMovie, object : JsonHelper.LoadMovieDetailCallBack {
            override fun loadMovieDetail(movie: MovieResponse) {
                result.value = ApiResponse.success(movie)
                IdlingResource.decrement()
            }

        })
        return result
    }

    fun getAllTvShow(): LiveData<ApiResponse<List<TvResponse>>> {
        val result = MutableLiveData<ApiResponse<List<TvResponse>>>()
        IdlingResource.increment()
        jsonHelper.getTvShow(object : JsonHelper.LoadTvCallBack {
            override fun loadTv(tv: ArrayList<TvResponse>) {
                result.value = ApiResponse.success(tv)
                IdlingResource.decrement()
            }
        })
        return result
    }

    fun getDetailTv(id: String): LiveData<ApiResponse<TvResponse>> {
        val result = MutableLiveData<ApiResponse<TvResponse>>()
        IdlingResource.increment()
        jsonHelper.getTvDetail(id, object : JsonHelper.LoadTvDetailCallBack {
            override fun loadTvDetail(tv: TvResponse) {
                result.value = ApiResponse.success(tv)
                IdlingResource.decrement()
            }

        })
        return result
    }

}

