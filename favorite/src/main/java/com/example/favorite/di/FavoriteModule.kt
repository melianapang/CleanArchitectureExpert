package com.example.favorite.di

import com.example.favorite.FavoriteViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel


val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}