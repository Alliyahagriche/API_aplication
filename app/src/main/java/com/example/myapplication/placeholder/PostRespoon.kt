package com.example.myapplication.placeholder

import com.google.gson.annotations.SerializedName

data class PostRespoon(
    val userId: Int,
    val id: Int,
    val title: String?,
    @SerializedName("body")
    val body: String?
)
