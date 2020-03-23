package com.azhara.jetpackdicodingsubmission3.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.azhara.jetpackdicodingsubmission3.data.source.remote.ApiResponse
import com.azhara.jetpackdicodingsubmission3.data.source.remote.StatusResponse
import com.azhara.jetpackdicodingsubmission3.utils.AppExecutor
import com.azhara.jetpackdicodingsubmission3.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val executor: AppExecutor) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType): Boolean

    abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS -> {
                    executor.diskoIO().execute {
                        saveCallResult(response.body)
                        executor.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                }
                StatusResponse.EMPTY -> {
                    executor.mainThread().execute {
                        result.addSource(loadFromDB()) { newData ->
                            result.value = Resource.success(newData)
                        }
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}