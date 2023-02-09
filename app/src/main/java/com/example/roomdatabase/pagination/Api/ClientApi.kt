package com.example.roomdatabase.pagination.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientApi {

    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .build()
            }
            return retrofit
        }
}