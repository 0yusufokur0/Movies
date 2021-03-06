package com.resurrection.movies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "search_item")
data class SearchItem(
    @PrimaryKey
    @SerializedName("imdbID")
    val imdbID: String,

    @SerializedName("Type")
    var type: String? = null,

    @SerializedName("Year")
    var year: String? = null,

    @SerializedName("Poster")
    var poster: String? = null,

    @SerializedName("Title")
    var title: String? = null,
)