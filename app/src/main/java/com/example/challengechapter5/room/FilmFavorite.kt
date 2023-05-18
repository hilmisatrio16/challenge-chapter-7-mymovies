package com.example.challengechapter5.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("unused")
@Entity
data class FilmFavorite(
    @PrimaryKey(autoGenerate = true) var id : Int = 0,
    val idFilm : Int,
    val emailUser : String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
)