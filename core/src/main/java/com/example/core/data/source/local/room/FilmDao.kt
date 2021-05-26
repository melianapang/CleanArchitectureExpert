package com.example.core.data.source.local.room

import androidx.room.*
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.ShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Query("SELECT * FROM movieEntity")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM showEntity")
    fun getAllShows(): Flow<List<ShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShows(shows: List<ShowEntity>)

    @Transaction
    @Query("SELECT * FROM movieEntity WHERE movieId = :filmId and genres is not null and run_time is not null")
    fun getSelectedMovieById(filmId: Int): Flow<MovieEntity>

    @Transaction
    @Query("SELECT * FROM showEntity WHERE showId = :filmId and genres is not null and episode_run_time is not null")
    fun getSelectedShowById(filmId: Int): Flow<ShowEntity>

    @Update
    fun updateSelectedMovie(movie: MovieEntity)

    @Update
    fun updateSelectedShow(show: ShowEntity)

    @Query("SELECT * FROM movieEntity WHERE is_favorite = 1")
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM showEntity WHERE is_favorite = 1")
    fun getAllFavoriteShows(): Flow<List<ShowEntity>>

    @Query("UPDATE movieEntity SET is_favorite = 1 WHERE movieId = :movieId")
    fun insertMovieFavorite(movieId: Int)

    @Query("UPDATE showEntity SET is_favorite = 1 WHERE showId = :showId")
    fun insertShowFavorite(showId: Int)

    @Query("UPDATE movieEntity SET is_favorite = 0 WHERE movieId = :filmId")
    fun deleteFavoriteMovieById(filmId: Int)

    @Query("UPDATE showEntity SET is_favorite = 0 WHERE showId = :filmId")
    fun deleteFavoriteShowById(filmId: Int)

    @Query("SELECT * FROM movieEntity WHERE title LIKE :name")
    fun searchAllMoviesByName(name: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM showEntity WHERE title LIKE :name")
    fun searchAllShowsByName(name: String): Flow<List<ShowEntity>>
}