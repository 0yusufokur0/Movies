package com.resurrection.movies.data.model

import androidx.room.PrimaryKey

data class MovieModel(
    @PrimaryKey
    val id: String,
    var title: String? = null,
    var year: String? = null,
    var rated: String? = null,
    var released: String? = null,
    var runtime: String? = null,
    var genre: String? = null,
    var director: String? = null,
    var writer: String? = null,
    var actors: String? = null,
    var plot: String? = null,
    var language: String? = null,
    var country: String? = null,
    var awards: String? = null,
    var poster: String? = null,
    var ratings: List<RatingsModel>? = null,
    var metascore: String? = null,
    var imdbRating: String? = null,
    var imdbVotes: String? = null,
    var imdbID: String? = null,
    var type: String? = null,
    var dVD: String? = null,
    var boxOffice: String? = null,
    var production: String? = null,
    var website: String? = null,
    var response: String? = null,
)
