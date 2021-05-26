package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Show(
    val id: Int,
    val title: String,
    val firstAirDate: String,
    val posterPath: String,
    val numOfEps: Int = 0,
    val numOfSeasons: Int = 0,
    val runTime: Int = 0,
    val overview: String = "",
    val genres: String = "",
    val isFavorite:Int = 0
) : Parcelable