package com.example.challengechapter5.viewmodel

import com.example.challengechapter5.model.ResponseDataFilm
import com.example.challengechapter5.model.ResponseDetailFilm
import com.example.challengechapter5.network.RestfulApi
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class FilmViewModelTest{
    private lateinit var api : RestfulApi

    @Before
    fun setup(){
        api = mockk()
    }
    @Test
    fun testRetriveDataFilmPopular() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponseDataFilm>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                api.getAllFilmPopular()
            }
        } returns responseRetrive
        val result = api.getAllFilmPopular()

        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.getAllFilmPopular()
            }
        }
        assertEquals(result,responseRetrive)
    }

    @Test
    fun testRetriveDataFilmTopRate() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponseDataFilm>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmTopRate().

        every {
            runBlocking {
                api.getAllFilmTopRate()
            }
        } returns responseRetrive
        val result = api.getAllFilmTopRate()

        //verify, kita memastikan bahwa metode api.getAllFilmTopRate() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.getAllFilmTopRate()
            }
        }
        assertEquals(result,responseRetrive)

    }

    @Test
    fun testRetriveDetailFilm() : Unit = runBlocking {
        val responseRetrive = mockk<Call<ResponseDetailFilm>>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDetailFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getDetailFilm().

        every {
            runBlocking {
                api.getDetailFilm(1)
            }
        } returns responseRetrive
        val result = api.getDetailFilm(1)

        //verify, kita memastikan bahwa metode api.getDetailFilm() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                api.getDetailFilm(1)
            }
        }
        assertEquals(result,responseRetrive)
    }
}