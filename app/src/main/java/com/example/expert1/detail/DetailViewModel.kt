package com.example.expert1.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Show
import com.example.core.domain.usecase.IUseCase
import com.example.core.valueobject.Resource

class DetailViewModel (private val useCase: IUseCase) : ViewModel() {
    private var movieId: Int = 0
    private var showId: Int = 0

    fun setSelectedMovie(movieId: Int) {
        this.movieId = movieId
    }

    fun setSelectedTvShow(showId: Int) {
        this.showId = showId
    }

    fun getSelectedMovie(): LiveData<Resource<Movie>> = useCase.getSelectedMovie(movieId).asLiveData()
    fun getSelectedTvShow(): LiveData<Resource<Show>> = useCase.getSelectedTvShow(showId).asLiveData()

    fun setFavoriteMovie(movieId: Int) = useCase.setFavoriteMovie(movieId)
    fun setFavoriteShow(showId : Int) = useCase.setFavoriteShow(showId)

    fun deleteFavoriteMovie(movieId: Int) = useCase.deleteFavoriteMovie(movieId)
    fun deleteFavoriteShow(showId : Int) = useCase.deleteFavoriteShow(showId)
}