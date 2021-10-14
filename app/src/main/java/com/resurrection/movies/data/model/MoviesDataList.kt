package com.resurrection.movies.data.model

import com.google.gson.annotations.SerializedName


data class MoviesDataList(
    @SerializedName("Response")
    var response: String,
    @SerializedName("Search")
    var search: List<MoviesHomeData>?
) {
    data class MoviesHomeData(
        @SerializedName("Title")
        var Title: String,
        @SerializedName("Year")
        var Year: String,
        @SerializedName("imdbID")
        var imdbID: String,
        @SerializedName("Type")
        var Type: String,
        @SerializedName("Poster")
        var Poster: String,
    )
}


