package com.example.challengechapter5.model


import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    @SerializedName("name")
    val name: String
)