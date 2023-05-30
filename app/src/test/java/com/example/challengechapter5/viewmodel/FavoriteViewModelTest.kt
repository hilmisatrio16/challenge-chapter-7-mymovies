package com.example.challengechapter5.viewmodel

import com.example.challengechapter5.room.FilmFavorite
import com.example.challengechapter5.room.FilmFavoriteDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FavoriteViewModelTest{
    private lateinit var dao : FilmFavoriteDao

    @Before
    fun setup(){
        dao = mockk()
    }

    @Test
    fun testRetriveDataFilmFavorite() : Unit = runBlocking {
        val responseRetrive = mockk<List<FilmFavorite>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                dao.getFilmFavorite("asd")
            }
        } returns responseRetrive
        val result = dao.getFilmFavorite("asd")

        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                dao.getFilmFavorite("asd")
            }
        }
        assertEquals(result,responseRetrive)
    }



}