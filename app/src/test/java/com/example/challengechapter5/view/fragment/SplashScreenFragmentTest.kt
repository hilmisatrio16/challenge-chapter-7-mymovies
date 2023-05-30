package com.example.challengechapter5.view.fragment

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SplashScreenFragmentTest{

    private lateinit var fragment: SplashScreenFragment

    @Before
    fun setup(){
        fragment = SplashScreenFragment()
    }

    @Test
    fun userIsActive(){
        val status = "active"

        val result = fragment.checkStatusUserActive(status)

        assertEquals(true, result)

    }

    @Test
    fun userIsNonActive(){
        val status = ""

        val result = fragment.checkStatusUserActive(status)

        assertEquals(false, result)
    }
}