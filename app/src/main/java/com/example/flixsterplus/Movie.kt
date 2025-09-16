package com.example.flixsterplus

import org.json.JSONObject

data class Movie(
    val title: String,
    val overview: String,
    val posterPath: String
) {
    companion object {
        fun fromJson(movieJson: JSONObject): Movie {
            val posterPath = movieJson.getString("poster_path")
            // Construct the full image URL
            val fullPosterUrl = "https://image.tmdb.org/t/p/w500/$posterPath"
            return Movie(
                movieJson.getString("title"),
                movieJson.getString("overview"),
                fullPosterUrl
            )
        }
    }
}
