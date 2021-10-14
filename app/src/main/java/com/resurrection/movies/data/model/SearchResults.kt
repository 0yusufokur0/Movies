package com.resurrection.movies.data.model

import com.google.gson.annotations.SerializedName
import com.resurrection.movies.data.model.SearchItem

data class SearchResults(
    @SerializedName("Response")
    var response: String? = null,

    @SerializedName("totalResults")
    var totalResults: String? = null,

    @SerializedName("Search")
    var search: List<SearchItem>? = null
) {


    override fun toString(): String {
        return "Response{" +
                "response = '" + response + '\''.toString() +
                ",totalResults = '" + totalResults + '\''.toString() +
                ",search = '" + search + '\''.toString() +
                "}"
    }


}