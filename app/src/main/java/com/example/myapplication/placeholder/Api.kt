package com.example.myapplication.placeholder

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("posts")
    fun getPosts(): Call<ArrayList<PostRespoon>>
}
