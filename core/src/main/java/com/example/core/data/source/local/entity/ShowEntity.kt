package com.example.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "showEntity")
data class ShowEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "showId")
    val id: Int = 0,

    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "poster_path")
    val posterPath: String,

    @Nullable
    @ColumnInfo(name = "episode_run_time")
    val runTime: Int = 0,

    @Nullable
    @ColumnInfo(name = "overview")
    val overview: String = "",

    @Nullable
    @ColumnInfo(name = "genres")
    val genres: String = "",

    @Nullable
    @ColumnInfo(name = "number_of_episodes")
    val numOfEps: Int = 0,

    @Nullable
    @ColumnInfo(name = "number_of_seasons")
    val numOfSeasons: Int = 0,

    @NonNull
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Int = 0
)