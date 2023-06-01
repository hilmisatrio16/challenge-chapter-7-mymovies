package com.example.challengechapter5.view.adapter

import com.example.challengechapter5.room.FilmFavorite
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FilmFavoriteAdapterTest{

    @MockK
    private lateinit var favoriteAdapter: FilmFavoriteAdapter

    @Before
    fun setup(){
        MockKAnnotations.init(this)

        favoriteAdapter  = FilmFavoriteAdapter(emptyList())
    }

    @Test
    fun tesSetValueFilm(){
        val resultFilm = listOf(
            FilmFavorite(1,1,"email",2.2,"link","23","title",23.2)
        )

        favoriteAdapter = FilmFavoriteAdapter(resultFilm)

        assertEquals(resultFilm.size, favoriteAdapter.itemCount)

    }
}