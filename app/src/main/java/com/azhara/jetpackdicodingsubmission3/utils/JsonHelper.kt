package com.azhara.jetpackdicodingsubmission3.utils

import android.util.Log
import com.azhara.jetpackdicodingsubmission3.BuildConfig
import com.azhara.jetpackdicodingsubmission3.data.source.remote.response.MovieResponse
import com.azhara.jetpackdicodingsubmission3.data.source.remote.response.TvResponse
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class JsonHelper {

    private val api_key = BuildConfig.API_KEY
    private val TAG = JsonHelper::class.java.simpleName

    fun getMovies(movieCallBack: LoadMovieCallBack) {

        val moviesList = ArrayList<MovieResponse>()
        val client = AsyncHttpClient()
        val url =
            "https://api.themoviedb.org/3/movie/top_rated?api_key=${api_key}&language=en-US&page=1"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val listMovieJson = responseObject.getJSONArray("results")
                    for (i in 0 until listMovieJson.length()) {
                        val movie = listMovieJson.getJSONObject(i)
                        val movieResponse = MovieResponse(
                            id = movie.getString("id"),
                            title = movie.getString("title"),
                            release_date = movie.getString("release_date"),
                            overview = movie.getString("overview"),
                            vote_average = movie.getString("vote_average"),
                            poster_path = movie.getString("poster_path"),
                            backdrop_path = movie.getString("backdrop_path")
                        )
                        moviesList.add(movieResponse)
                        Log.d(TAG, "$moviesList")
                    }
                    movieCallBack.loadMovie(moviesList)

                } catch (e: Exception) {
                    Log.e(TAG, "Error onSuccess: $e")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e(TAG, "onFailure: $error")
            }

        })

    }

    fun getDetailMovie(movieId: String, callback: LoadMovieDetailCallBack) {
        val movie = MovieResponse()
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/movie/${movieId}?api_key=${api_key}&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)
                movie.id = responseObject.getString("id")
                movie.backdrop_path = responseObject.getString("backdrop_path")
                movie.poster_path = responseObject.getString("poster_path")
                movie.release_date = responseObject.getString("release_date")
                movie.overview = responseObject.getString("overview")
                movie.title = responseObject.getString("title")
                movie.vote_average = responseObject.getString("vote_average")
                callback.loadMovieDetail(movie)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        })

    }


    fun getTvShow(tvCallBack: LoadTvCallBack) {

        val tvList = ArrayList<TvResponse>()
        val client = AsyncHttpClient()
        val url =
            "https://api.themoviedb.org/3/tv/top_rated?api_key=${api_key}&language=en-US&page=1"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val listTvJson = responseObject.getJSONArray("results")
                    for (i in 0 until listTvJson.length()) {
                        val tv = listTvJson.getJSONObject(i)
                        val tvResponse = TvResponse(
                            id = tv.getString("id"),
                            name = tv.getString("name"),
                            first_air_date = tv.getString("first_air_date"),
                            vote_average = tv.getString("vote_average"),
                            overview = tv.getString("overview"),
                            poster_path = tv.getString("poster_path"),
                            backdrop_path = tv.getString("backdrop_path")

                        )
                        tvList.add(tvResponse)
                    }
                    tvCallBack.loadTv(tvList)
                } catch (e: Exception) {
                    Log.e(TAG, "Error onSucces: $e")
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.e(TAG, "onFailure: $error")
            }

        })
    }

    fun getTvDetail(id: String, tvCallBack: LoadTvDetailCallBack) {

        val tvResponse = TvResponse()
        val client = AsyncHttpClient()
        val url = "https://api.themoviedb.org/3/tv/${id}?api_key=${api_key}&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                val responseObject = JSONObject(result)
                tvResponse.id = responseObject.getString("id")
                tvResponse.name = responseObject.getString("name")
                tvResponse.overview = responseObject.getString("overview")
                tvResponse.first_air_date = responseObject.getString("first_air_date")
                tvResponse.vote_average = responseObject.getString("vote_average")
                tvResponse.backdrop_path = responseObject.getString("backdrop_path")
                tvResponse.poster_path = responseObject.getString("poster_path")
                tvCallBack.loadTvDetail(tvResponse)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                TODO("Not yet implemented")
            }

        })

    }

    interface LoadMovieDetailCallBack {
        fun loadMovieDetail(movie: MovieResponse)
    }

    interface LoadMovieCallBack {
        fun loadMovie(movie: ArrayList<MovieResponse>)
    }

    interface LoadTvCallBack {
        fun loadTv(tv: ArrayList<TvResponse>)
    }

    interface LoadTvDetailCallBack {
        fun loadTvDetail(tv: TvResponse)
    }

}