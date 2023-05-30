package com.example.challengechapter5.viewmodel

import com.example.challengechapter5.room.User
import com.example.challengechapter5.room.UserDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserViewModelTest{
    private lateinit var dao : UserDao

    @Before
    fun setup(){
        dao = mockk()
    }

    @Test
    fun testRetriveDataUser() : Unit = runBlocking {
        val responseRetrive = mockk<User>()

        //membuat objek palsu (mock) responseRetrive dari kelas <Call<ResponseDataFilm>>
        //Objek palsu ini akan digunakan sebagai respons palsu dari pemanggilan api.getAllFilmPopular().

        every {
            runBlocking {
                dao.getProfileUser("user")
            }
        } returns responseRetrive
        val result = dao.getProfileUser("user")

        //verify, kita memastikan bahwa metode api.getAllFilmPopular() benar-benar dipanggil dengan argumen yang sesuai.

        verify {
            runBlocking {
                dao.getProfileUser("user")
            }
        }
        assertEquals(result,responseRetrive)
    }
}