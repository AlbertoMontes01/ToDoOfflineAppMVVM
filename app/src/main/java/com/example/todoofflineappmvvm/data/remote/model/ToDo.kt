package com.example.todoofflineappmvvm.data.remote.model

data class ToDo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
