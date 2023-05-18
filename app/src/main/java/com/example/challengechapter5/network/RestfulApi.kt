package com.example.challengechapter5.network

import com.example.challengechapter5.model.ResponseDataFilm
import com.example.challengechapter5.model.ResponseDetailFilm
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestfulApi {

    @GET("movie/popular?api_key=dd42f3fa66fbe4c2e71096a7d64847c3&language=en-US&page=1")
    fun getAllFilmPopular() : Call<ResponseDataFilm>

    @GET("movie/{id}?api_key=dd42f3fa66fbe4c2e71096a7d64847c3&language=en-US")
    fun getDetailFilm(@Path("id") id : Int) : Call<ResponseDetailFilm>

    @GET("movie/top_rated?api_key=dd42f3fa66fbe4c2e71096a7d64847c3&language=en-US&page=1")
    fun getAllFilmTopRate() : Call<ResponseDataFilm>



}