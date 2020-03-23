package com.azhara.jetpackdicodingsubmission3.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.azhara.jetpackdicodingsubmission3.data.source.local.LocalDataSource
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.MoviesEntity
import com.azhara.jetpackdicodingsubmission3.data.source.local.entity.TvEntity
import com.azhara.jetpackdicodingsubmission3.data.source.remote.ApiResponse
import com.azhara.jetpackdicodingsubmission3.data.source.remote.RemoteDataSource
import com.azhara.jetpackdicodingsubmission3.data.source.remote.response.MovieResponse
import com.azhara.jetpackdicodingsubmission3.data.source.remote.response.TvResponse
import com.azhara.jetpackdicodingsubmission3.utils.AppExecutor
import com.azhara.jetpackdicodingsubmission3.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutor: AppExecutor
) : IMovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutor: AppExecutor
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localDataSource, appExecutor)
            }

    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MoviesEntity>, List<MovieResponse>>(appExecutor) {
            override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(3)
                    .setPageSize(3)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            override fun saveCallResult(data: List<MovieResponse>) {
                val moviesList = ArrayList<MoviesEntity>()
                for (movieData in data) {
                    val movie =
                        MoviesEntity(
                            id = movieData.id,
                            title = movieData.title,
                            release_date = movieData.release_date,
                            overview = movieData.overview,
                            vote_average = movieData.vote_average,
                            poster_path = movieData.poster_path,
                            backdrop_path = movieData.backdrop_path
                        )
                    moviesList.add(movie)
                    Log.d("MovieRepo", "$moviesList")
                }
                localDataSource.InsertAllMovie(moviesList)
            }

        }.asLiveData()
    }

    override fun setFavoriteMovie(movie: MoviesEntity, state: Boolean) =
        appExecutor.diskoIO().execute { localDataSource.updateMovie(movie, state) }


    override fun getMovieFavorite(): LiveData<PagedList<MoviesEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(3)
            .setPageSize(3)
            .build()

        return LivePagedListBuilder(localDataSource.getFavoriteMovie(), config).build()

    }

    override fun getMovieDetail(movieId: String): LiveData<Resource<MoviesEntity>> {
        return object : NetworkBoundResource<MoviesEntity, MovieResponse>(appExecutor) {
            override fun loadFromDB(): LiveData<MoviesEntity> =
                localDataSource.getMovieById(movieId)

            override fun shouldFetch(data: MoviesEntity): Boolean =
                data.id == null || data.id.isEmpty()

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: MovieResponse) {
                val movie = MoviesEntity(
                    id = data.id,
                    overview = data.overview,
                    title = data.title,
                    backdrop_path = data.backdrop_path,
                    poster_path = data.poster_path,
                    vote_average = data.vote_average,
                    favorite = false
                )
                localDataSource.insertMovie(movie)
            }

        }.asLiveData()
    }

    override fun getAllTv(): LiveData<Resource<PagedList<TvEntity>>> {
        return object : NetworkBoundResource<PagedList<TvEntity>, List<TvResponse>>(appExecutor) {
            override fun loadFromDB(): LiveData<PagedList<TvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(3)
                    .setPageSize(3)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTv(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvEntity>): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvResponse>>> =
                remoteDataSource.getAllTvShow()

            override fun saveCallResult(data: List<TvResponse>) {
                val tvData = ArrayList<TvEntity>()
                for (tvList in data) {
                    val tv = TvEntity(
                        id = tvList.id,
                        name = tvList.name,
                        first_air_date = tvList.first_air_date,
                        overview = tvList.overview,
                        vote_average = tvList.vote_average,
                        backdrop_path = tvList.backdrop_path,
                        poster_path = tvList.poster_path,
                        favorite = false
                    )
                    tvData.add(tv)
                }
                localDataSource.insertAllTv(tvData)
            }

        }.asLiveData()
    }

    override fun getTvFavorite(): LiveData<PagedList<TvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(3)
            .setPageSize(3)
            .build()
        return LivePagedListBuilder(localDataSource.getAllTvFavorite(), config).build()
    }

    override fun getTvDetail(tvId: String): LiveData<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, TvResponse>(appExecutor) {
            override fun loadFromDB(): LiveData<TvEntity> = localDataSource.getTvById(tvId)

            override fun shouldFetch(data: TvEntity): Boolean = data.id == null || data.id.isEmpty()

            override fun createCall(): LiveData<ApiResponse<TvResponse>> =
                remoteDataSource.getDetailTv(tvId)

            override fun saveCallResult(data: TvResponse) {
                val tv = TvEntity()
                tv.id = data.id
                tv.name = data.name
                tv.overview = data.overview
                tv.first_air_date = data.first_air_date
                tv.vote_average = data.vote_average
                tv.poster_path = data.poster_path
                tv.backdrop_path = data.backdrop_path
                tv.favorite = false
                localDataSource.insertTvDetail(tv)
            }

        }.asLiveData()
    }

    override fun setFavoriteTv(tvEntity: TvEntity, newState: Boolean) {
        appExecutor.diskoIO().execute { localDataSource.updateTv(tvEntity, newState) }
    }
}