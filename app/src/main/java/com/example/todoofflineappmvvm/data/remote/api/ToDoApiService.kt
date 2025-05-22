package com.example.todoofflineappmvvm.data.remote.api

import com.example.todoofflineappmvvm.data.remote.model.ToDo
import retrofit2.http.GET

interface ToDoApiService {
    @GET("todos")
    suspend fun getAllToDo(): List<ToDo>
}