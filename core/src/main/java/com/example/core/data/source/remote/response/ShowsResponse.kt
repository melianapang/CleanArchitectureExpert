package com.example.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ShowsResponse(
    @SerializedName("results")
    val results: ArrayList<ShowsObject> = ArrayList()
)

@Parcelize
data class ShowsObject(
    val id: Int,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("poster_path")
    val posterPath: String,
) : Parcelable


data class ShowsDetailsResponse(
    val id: Int = 0,
    @SerializedName("name")
    val title: String = "",
    @SerializedName("first_air_date")
    val firstAirDate: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("number_of_episodes")
    val numOfEps: Int = 0,
    @SerializedName("number_of_seasons")
    val numOfSeasons: Int = 0,
    @SerializedName("episode_run_time")
    val runTime: ArrayList<Int> = ArrayList(),
    val overview: String = "",
    @SerializedName("genres")
    val genres: ArrayList<Genres> = ArrayList()
)
