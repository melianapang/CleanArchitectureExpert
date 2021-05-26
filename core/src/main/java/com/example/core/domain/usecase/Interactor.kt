package com.example.core.domain.usecase

import com.example.core.domain.model.Movie
import com.example.core.domain.model.Show
import com.example.core.domain.repository.IRepository
import com.example.core.valueobject.Resource
import kotlinx.coroutines.flow.Flow

class Interactor(private val repository: IRepository) : IUseCase {
    override fun getAllMovies(): Flow<Resource<List<Movie>>> = repository.getAllMovies()

    override fun getAllShows(): Flow<Resource<List<Show>>> = repository.getAllShows()

    override fun getSelectedTvShow(showId: Int): Flow<Resource<Show>> = repository.getSelectedTvShow(showId)

    override fun getSelectedMovie(movieId: Int) : Flow<Resource<Movie>> = repository.getSelectedMovie(movieId)

    override fun getAllFavoriteShows(): Flow<List<Show>> = repository.getAllFavoriteShows()

    override fun getAllFavoriteMovies(): Flow<List<Movie>> = repository.getAllFavoriteMovies()

    override fun setFavoriteMovie(movieId: Int) = repository.setFavoriteMovie(movieId)

    override fun setFavoriteShow(showId: Int) = repository.setFavoriteShow(showId)

    override fun deleteFavoriteMovie(filmId: Int) = repository.deleteFavoriteMovie(filmId)

    override fun deleteFavoriteShow(filmId: Int) = repository.deleteFavoriteShow(filmId)

    override fun searchAllMovies(name: String): Flow<List<Movie>> = repository.searchAllMovies(name)

    override fun searchAllShows(name: String) : Flow<List<Show>> = repository.searchAllShows(name)

}