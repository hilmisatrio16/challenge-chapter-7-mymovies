package com.example.challengechapter5.model


import com.google.gson.annotations.SerializedName

@Suppress("unused")
data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)