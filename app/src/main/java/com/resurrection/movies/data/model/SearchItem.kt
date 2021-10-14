package com.resurrection.movies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class SearchItem(
    @PrimaryKey
    @SerializedName("imdbID")
    var imdbID: String,
    @SerializedName("Type")
    var type: String? = null,

    @SerializedName("Year")
    var year: String? = null,

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