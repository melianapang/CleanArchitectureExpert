package com.example.expert1.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.model.Movie
import com.example.core.domain.model.Show
import com.example.core.domain.usecase.IUseCase
import com.example.core.valueobject.Resource

class HomeViewModel(private val useCase: IUseCase) : ViewModel() {
    fun getFilm(): LiveData<Resource<List<Movie>>> = useCase.getAllMovies().asLiveData()
    fun getTvShows(): LiveData<Resource<List<Show>>> = useCase.getAllShows().asLiveData()

    fun searchFilm(name:String): LiveData<List<Movie>> = useCase.searchAllMovies(name).asLiveData()
    fun searchTvShows(name:String): LiveData<List<Show>> = useCase.searchAllShows(name).asLiveData()
}