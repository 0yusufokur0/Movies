package com.resurrection.movies.data.model

import com.google.gson.annotations.SerializedName

data class SearchItem(
    @SerializedName("Type")
    var type: String? = null,

    @SerializedName("Year")
    var year: String? = null,

    @SerializedName("imdbID")
    var imdbID: String? = null,

    @SerializedName("Poster")
    var poster: String? = null,

    @SerializedName("Title")
    var title: String? = null,
) {


    override fun toString(): String {
        return "SearchItem{" +
                "type = '" + type + '\''.toString() +
                ",year = '" + year + '\''.toString() +
                ",imdbID = '" + imdbID + '\''.toString() +
                ",poster = '" + poster + '\''.toString() +
                ",title = '" + title + '\''.toString() +
                "}"
    }
}