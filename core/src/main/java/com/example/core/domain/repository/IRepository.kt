package com.example.core.domain.repository

import com.example.core.domain.model.Movie
import com.example.core.domain.model.Show
import com.example.core.valueobject.Resource
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun getAllShows(): Flow<Resource<List<Show>>>

    fun getSelectedTvShow(showId: Int): Flow<Resource<Show>>
    fun getSelectedMovie(movieId: Int): Flow<Resource<Movie>>

    fun getAllFavoriteShows(): Flow<List<Show>>
    fun getAllFavoriteMovies(): Flow<List<Movie>>

    fun setFavoriteMovie(movieId: Int)
    fun setFavoriteShow(showId: Int)

    fun deleteFavoriteMovie(filmId: Int)
    fun deleteFavoriteShow(filmId: Int)

    fun searchAllMovies(name: String): Flow<List<Movie>>
    fun searchAllShows(name: String): Flow<List<Show>>
}