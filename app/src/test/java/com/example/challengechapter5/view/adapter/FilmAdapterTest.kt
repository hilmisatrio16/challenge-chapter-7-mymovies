package com.example.challengechapter5.view.adapter

import com.example.challengechapter5.model.ResultFilm
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FilmAdapterTest{

    @MockK
    private lateinit var listFilm : List<ResultFilm>

    @MockK
    private lateinit var filmAdapter : FilmAdapter

    @Before
    fun setup(){
        MockKAnnotations.init(this)

        filmAdapter  = FilmAdapter(emptyList())
    }

    @Test
    fun testItemCount(){
        every {
            listFilm.size
        } returns 10

        val filmAdapter = FilmAdapter(listFilm)

        val itemCountList = filmAdapter.itemCount

        assertEquals(10, itemCountList)
    }

    @Test
    fun tesSetValueFilm(){
        val resultFilm = listOf(
            ResultFilm(false,"kosong", listOf(1),1,"en",
                "title","des",1233.99, "12","23",
                "titi",false,123.2,123)
        )

        filmAdapter = FilmAdapter(resultFilm)

        assertEquals(resultFilm.size, filmAdapter.itemCount)

    }
}