package com.example.core.data.source.remote.api

import com.example.core.data.source.remote.response.MovieDetailsResponse
import com.example.core.data.source.remote.response.MovieResponse
import com.example.core.data.source.remote.response.ShowsDetailsResponse
import com.example.core.data.source.remote.response.ShowsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiEndPoint {
    @GET("discover/movie")
    suspend fun getAllMoviesFromAPI(@Query("api_key") apiKey: String): Response<MovieResponse>

    @GET("discover/tv")
    suspend fun getAllShowsFromAPI(@Query("api_key") apiKey: String): Response<ShowsResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovieFromAPI(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String
    ): Response<MovieDetailsResponse>

    @GET("tv/{tv_id}")
    suspend fun getDetailShowFromAPI(
        @Path("tv_id") id: Int,
        @Query("api_key") key: String
    ): Response<ShowsDetailsResponse>
}