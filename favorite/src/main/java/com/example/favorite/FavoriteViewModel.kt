package com.example.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Show
import com.example.core.domain.usecase.IUseCase

class FavoriteViewModel(private val useCase: IUseCase) : ViewModel()  {
    fun getFavoriteFilm(): LiveData<List<Movie>> = useCase.getAllFavoriteMovies().asLiveData()
    fun getFavoriteTvShows(): LiveData<List<Show>> = useCase.getAllFavoriteShows().asLiveData()
}