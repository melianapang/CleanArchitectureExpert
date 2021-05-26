package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val posterPath: String,
    val runTime: Int = 0,
    val overview: String = "",
    val genres: String="",
    val isFavorite:Int = 0
) : Parcelable