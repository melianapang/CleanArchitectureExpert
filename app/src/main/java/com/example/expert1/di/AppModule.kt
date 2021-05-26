package com.example.expert1.di

import com.example.core.domain.usecase.IUseCase
import com.example.core.domain.usecase.Interactor
import com.example.expert1.detail.DetailViewModel
import com.example.expert1.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IUseCase> { Interactor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}