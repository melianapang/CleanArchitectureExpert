package com.example.core.data.source.local

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.ShowEntity
import com.example.core.data.source.local.room.FilmDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val filmDao: FilmDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = filmDao.getAllMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = filmDao.insertMovies(movies)

    fun getAllShows(): Flow<List<ShowEntity>> = filmDao.getAllShows()

    suspend fun insertShows(shows: List<ShowEntity>) = filmDao.insertShows(shows)

    fun getSelectedMovieById(filmId: Int): Flow<MovieEntity> =
        filmDao.getSelectedMovieById(filmId)

    fun getSelectedShowById(filmId: Int): Flow<ShowEntity> = filmDao.getSelectedShowById(filmId)

    fun updateSelectedShow(show: ShowEntity) = filmDao.updateSelectedShow(show)

    fun updateSelectedMovie(movie: MovieEntity) = filmDao.updateSelectedMovie(movie)

    fun getAllFavoriteMovies(): Flow<List<MovieEntity>> =
        filmDao.getAllFavoriteMovies()

    fun getAllFavoriteShows(): Flow<List<ShowEntity>> = filmDao.getAllFavoriteShows()

    fun insertMovieFavoriteById(movieId: Int) = filmDao.insertMovieFavorite(movieId)

    fun insertShowFavoriteById(showId: Int) = filmDao.insertShowFavorite(showId)

    fun deleteFavoriteMovieById(filmId: Int) = filmDao.deleteFavoriteMovieById(filmId)

    fun deleteFavoriteShowById(filmId: Int) = filmDao.deleteFavoriteShowById(filmId)

    fun searchAllMovies(name: String): Flow<List<MovieEntity>> =
        filmDao.searchAllMoviesByName(name)

    fun searchAllShows(name: String): Flow<List<ShowEntity>> =
        filmDao.searchAllShowsByName(name)
}