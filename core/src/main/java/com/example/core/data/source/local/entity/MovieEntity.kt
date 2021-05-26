package com.example.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieEntity")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var id: Int = 0,

    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "poster_path")
    var posterPath: String,

    @Nullable
    @ColumnInfo(name = "run_time")
    var runTime: Int=0,

    @Nullable
    @ColumnInfo(name = "overview")
    var overview: String = "",

    @Nullable
    @ColumnInfo(name = "genres")
    var genres: String = "",

    @NonNull
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Int = 0
)