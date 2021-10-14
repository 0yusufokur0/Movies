package com.resurrection.movies.data.model

import com.google.gson.annotations.SerializedName

data class SearchResults(
    @SerializedName("Response")
    var response: String? = null,

    @SerializedName("totalResults")
    var totalResults: String? = null,

    @SerializedName("Search")
    var search: List<SearchItem>? = null
)