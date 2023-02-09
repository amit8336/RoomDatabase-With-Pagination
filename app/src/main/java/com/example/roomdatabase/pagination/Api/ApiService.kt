package com.example.roomdatabase.pagination.Api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/photos")
//    fun getMovies(@Query("albumId=") albumId: Int): Call<List<Movie>>
    fun getData(@Query("albumId") albumId: Int) : Call<List<ApiModel>>
}