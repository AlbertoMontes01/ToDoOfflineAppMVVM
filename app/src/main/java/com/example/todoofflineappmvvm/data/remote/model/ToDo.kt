package com.example.todoofflineappmvvm.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ToDo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
