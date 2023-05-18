package com.example.challengechapter5.model


import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)