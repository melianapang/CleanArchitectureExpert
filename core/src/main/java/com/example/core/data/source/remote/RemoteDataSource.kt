package com.example.core.data.source.remote

import android.util.Log
import com.bumptech.glide.load.HttpException
import com.example.core.data.source.remote.api.ApiEndPoint
import com.example.core.data.source.remote.response.MovieDetailsResponse
import com.example.core.data.source.remote.response.MovieObject
import com.example.core.data.source.remote.response.ShowsDetailsResponse
import com.example.core.data.source.remote.response.ShowsObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiEndPoint) {

    companion object {
        private const val TAG = "RemoteDataSource"
        private const val API_KEY = "af28284beab32ee6b8b90560faf0201f"
    }

    suspend fun getAllMovies(): Flow<ApiResponse<ArrayList<MovieObject>>> {
        return flow {
            try {
                val response = apiService.getAllMoviesFromAPI(API_KEY)
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(ApiResponse.Success(responseBody.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllShows(): Flow<ApiResponse<ArrayList<ShowsObject>>> {
        return flow {
            try {
                val response = apiService.getAllShowsFromAPI(API_KEY)
                val responseBody = response.body()
                if (responseBody != null){
                    emit(ApiResponse.Success(responseBody.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSelectedMovie(id: Int): Flow<ApiResponse<MovieDetailsResponse>> {
        return flow {
            try {
                val response = apiService.getDetailMovieFromAPI(id, API_KEY)
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(ApiResponse.Success(responseBody))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSelectedShow(id: Int): Flow<ApiResponse<ShowsDetailsResponse>> {
        return flow {
            try {
                val response = apiService.getDetailShowFromAPI(id, API_KEY)
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(ApiResponse.Success(responseBody))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}