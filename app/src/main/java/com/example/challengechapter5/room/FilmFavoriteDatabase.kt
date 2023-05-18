package com.example.challengechapter5.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FilmFavorite::class], version = 1, exportSchema = false)
abstract class FilmFavoriteDatabase : RoomDatabase() {

    abstract fun filmFavoriteDao() : FilmFavoriteDao

}