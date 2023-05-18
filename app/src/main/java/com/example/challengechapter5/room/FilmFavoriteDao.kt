package com.example.challengechapter5.room

import androidx.room.*

@Dao
interface FilmFavoriteDao {
    @Query("SELECT * FROM FilmFavorite WHERE emailUser = :emailuser ")
    fun getFilmFavorite(emailuser : String) : List<FilmFavorite>

    @Query("SELECT * FROM FilmFavorite WHERE idFilm = :idFilm AND emailUser = :emailuser")
    fun getDetailFilmFavorite(idFilm : Int, emailuser : String) : FilmFavorite

    @Insert
    fun insertFilmFavorite(filmFavorite: FilmFavorite)

    @Delete
    fun deleteFilmFavorite(filmFavorite: FilmFavorite)
}