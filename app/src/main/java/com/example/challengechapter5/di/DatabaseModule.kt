package com.example.challengechapter5.di

import android.content.Context
import androidx.room.Room
import com.example.challengechapter5.room.FilmFavoriteDao
import com.example.challengechapter5.room.FilmFavoriteDatabase
import com.example.challengechapter5.room.UserDao
import com.example.challengechapter5.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabaseUser(@ApplicationContext context : Context) : UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDaoUser(userDatabase: UserDatabase) : UserDao {
        return userDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideDatatabaseFilmFavorite(@ApplicationContext context: Context)  : FilmFavoriteDatabase {
        return Room.databaseBuilder(context, FilmFavoriteDatabase::class.java, "favorite_database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFilmFavoriteDao(filmFavoriteDatabase: FilmFavoriteDatabase) : FilmFavoriteDao {
        return filmFavoriteDatabase.filmFavoriteDao()
    }

}