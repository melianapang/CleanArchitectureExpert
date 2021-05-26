package com.example.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieResponse(
    @SerializedName("results")
    val results: ArrayList<MovieObject> = ArrayList()
)

@Parcelize
data class MovieObject(
    @SerializedName("id")
    val id: Int,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
) : Parcelable

data class MovieDetailsResponse(
    val id: Int,
    @SerializedName("original_title")
    val title: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("runtime")
    val runTime: Int,
    val overview: String,
    @SerializedName("genres")
    val genres: ArrayList<Genres> = ArrayList()
)
