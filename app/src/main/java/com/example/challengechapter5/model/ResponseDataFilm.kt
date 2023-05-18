package com.example.challengechapter5.model


import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class ResponseDataFilm(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<ResultFilm>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)