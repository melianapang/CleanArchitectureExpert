package com.example.core.data

import com.example.core.domain.model.Movie
import com.example.core.domain.model.Show
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.remote.ApiResponse
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.response.MovieDetailsResponse
import com.example.core.data.source.remote.response.MovieObject
import com.example.core.data.source.remote.response.ShowsDetailsResponse
import com.example.core.data.source.remote.response.ShowsObject
import com.example.core.domain.repository.IRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.MappingHelper
import com.example.core.valueobject.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource,
        private val appExecutors: AppExecutors
) : IRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> {
        return object :
                NetworkBoundResource<List<Movie>, ArrayList<MovieObject>>() {
            public override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map { MappingHelper.mapMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                    data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<ArrayList<MovieObject>>> =
                    remoteDataSource.getAllMovies()

            override suspend fun saveCallResult(data: ArrayList<MovieObject>) {
                val filmList = MappingHelper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(filmList)
            }
        }.asFlow()
    }

    override fun getAllShows(): Flow<Resource<List<Show>>> {
        return object :
                NetworkBoundResource<List<Show>, ArrayList<ShowsObject>>() {
            public override fun loadFromDB(): Flow<List<Show>> {
                return localDataSource.getAllShows().map { MappingHelper.mapShowEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Show>?): Boolean =
                    data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<ArrayList<ShowsObject>>> =
                    remoteDataSource.getAllShows()

            override suspend fun saveCallResult(data: ArrayList<ShowsObject>) {
                val filmList = MappingHelper.mapShowResponsesToEntities(data)
                localDataSource.insertShows(filmList)
            }

        }.asFlow()
    }

    override fun getSelectedMovie(movieId: Int): Flow<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, MovieDetailsResponse>() {
            public override fun loadFromDB(): Flow<Movie> =
                localDataSource.getSelectedMovieById(movieId).map { MappingHelper.mapMovieEntityToDomain(it) }

            override fun shouldFetch(data: Movie?): Boolean =
                    data == null || data.genres == ""

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailsResponse>> =
                    remoteDataSource.getSelectedMovie(movieId)

            override suspend fun saveCallResult(data: MovieDetailsResponse) {
                appExecutors.diskIO().execute {
                    localDataSource.updateSelectedMovie(
                        MappingHelper.mapMovieSelectedDetailResponseToEntity(data)
                    )
                }
            }
        }.asFlow()
    }

    override fun getSelectedTvShow(showId: Int): Flow<Resource<Show>> {
        return object : NetworkBoundResource<Show, ShowsDetailsResponse>() {
            public override fun loadFromDB(): Flow<Show> =
                    localDataSource.getSelectedShowById(showId).map { MappingHelper.mapShowEntityToDomain(it) }

            override fun shouldFetch(data: Show?): Boolean =
                    data == null || data.genres == ""

            override suspend fun createCall(): Flow<ApiResponse<ShowsDetailsResponse>> =
                    remoteDataSource.getSelectedShow(showId)

            override suspend fun saveCallResult(data: ShowsDetailsResponse) {
                appExecutors.diskIO().execute {
                    localDataSource.updateSelectedShow(
                        MappingHelper.mapShowSelectedDetailResponseToEntity(data)
                    )
                }
            }

        }.asFlow()
    }

    override fun getAllFavoriteShows(): Flow<List<Show>> {
        return localDataSource.getAllFavoriteShows().map { MappingHelper.mapShowEntitiesToDomain(it) }
    }

    override fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteMovies().map { MappingHelper.mapMovieEntitiesToDomain(it) }
    }

    override fun setFavoriteMovie(movieId: Int) {
        appExecutors.diskIO().execute { localDataSource.insertMovieFavoriteById(movieId) }
    }

    override fun setFavoriteShow(showId: Int) {
        appExecutors.diskIO().execute { localDataSource.insertShowFavoriteById(showId) }
    }

    override fun deleteFavoriteMovie(filmId: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteMovieById(filmId) }
    }

    override fun deleteFavoriteShow(filmId: Int) {
        appExecutors.diskIO().execute { localDataSource.deleteFavoriteShowById(filmId) }
    }

    override fun searchAllMovies(name: String): Flow<List<Movie>> {
        return localDataSource.searchAllMovies(name).map { MappingHelper.mapMovieEntitiesToDomain(it) }
    }

    override fun searchAllShows(name: String): Flow<List<Show>> {
        return localDataSource.searchAllShows(name).map { MappingHelper.mapShowEntitiesToDomain(it) }
    }
}
