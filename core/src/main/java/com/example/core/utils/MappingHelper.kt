package com.example.core.utils

import com.example.core.domain.model.Movie
import com.example.core.domain.model.Show
import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.local.entity.ShowEntity
import com.example.core.data.source.remote.response.*

object MappingHelper {
    private const val POSTER_PATH_LINK = "https://image.tmdb.org/t/p/w500"

    private fun mapGenres(genres: ArrayList<Genres>?): String {
        val genreStr = StringBuilder()
        val genreSize = genres?.size ?: 0
        for (i in 0 until genreSize - 1) {
            val genre = genres?.get(i)?.name
            genreStr.append(genre)
            if (i < genreSize - 2) genreStr.append(", ")
        }

        return genreStr.toString()
    }

    fun mapPosterPath(data: String?): String {
        val pathStr = StringBuilder()
        pathStr.append(POSTER_PATH_LINK).append(data)
        return pathStr.toString()
    }

    fun mapShowResponsesToEntities(data: List<ShowsObject>): List<ShowEntity> {
        val filmList = ArrayList<ShowEntity>()
        for (response in data) {
            val film = ShowEntity(
                id = response.id,
                firstAirDate = response.firstAirDate,
                title = response.title,
                posterPath = response.posterPath,
                runTime = 0,
                overview = "",
                genres = "",
                numOfEps = 0,
                numOfSeasons = 0,
                isFavorite = 0
            )
            filmList.add(film)
        }
        return filmList
    }

    fun mapMovieResponsesToEntities(data: List<MovieObject>): List<MovieEntity> {
        val filmList = ArrayList<MovieEntity>()
        for (response in data) {
            val film = MovieEntity(
                id = response.id,
                releaseDate = response.releaseDate,
                title = response.title,
                posterPath = response.posterPath,
                runTime = 0,
                overview = "",
                genres = "",
                isFavorite = 0
            )
            filmList.add(film)
        }
        return filmList
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                overview = it.overview ,
                title = it.title,
                releaseDate = it.releaseDate,
                posterPath = it.posterPath,
                runTime = it.runTime,
                genres = it.genres,
                isFavorite = it.isFavorite
            )
        }

    fun mapShowEntitiesToDomain(input: List<ShowEntity>): List<Show> =
        input.map {
            Show(
                id = it.id,
                overview = it.overview,
                title = it.title,
                firstAirDate = it.firstAirDate,
                posterPath = it.posterPath,
                runTime = it.runTime,
                genres = it.genres,
                numOfSeasons = it.numOfSeasons,
                numOfEps = it.numOfEps,
                isFavorite = it.isFavorite
            )
        }

    fun mapMovieEntityToDomain(input: MovieEntity) : Movie = Movie(
        id = input.id,
        overview = input.overview,
        title = input.title,
        releaseDate = input.releaseDate,
        posterPath = input.posterPath,
        runTime = input.runTime,
        genres = input.genres,
        isFavorite = input.isFavorite
    )

    fun mapShowEntityToDomain(input: ShowEntity) : Show = Show(
        id = input.id,
        overview = input.overview,
        title = input.title,
        firstAirDate = input.firstAirDate,
        posterPath = input.posterPath,
        runTime = input.runTime,
        genres = input.genres,
        numOfSeasons = input.numOfSeasons,
        numOfEps = input.numOfEps,
        isFavorite = input.isFavorite
    )

    fun mapMovieSelectedDetailResponseToEntity(data:MovieDetailsResponse) : MovieEntity{
        val genre = mapGenres(data.genres)
        return MovieEntity(
            id = data.id,
            releaseDate = data.releaseDate,
            title = data.title,
            posterPath = data.posterPath,
            runTime = data.runTime,
            overview = data.overview,
            genres = genre,
            isFavorite = 0
        )
    }

    fun mapShowSelectedDetailResponseToEntity(data:ShowsDetailsResponse): ShowEntity{
        val genre = mapGenres(data.genres)
        return ShowEntity(
            id = data.id,
            firstAirDate = data.firstAirDate,
            title = data.title,
            posterPath = data.posterPath,
            runTime = data.runTime[0],
            overview = data.overview,
            genres = genre,
            numOfEps = data.numOfEps,
            numOfSeasons = data.numOfSeasons,
            isFavorite = 0
        )
    }
}