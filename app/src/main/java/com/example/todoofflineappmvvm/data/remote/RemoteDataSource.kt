package com.example.todoofflineappmvvm.data.remote

import com.example.todoofflineappmvvm.data.remote.api.ToDoApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: ToDoApiService
) {
    suspend fun fetchTodos() = api.getAllToDo()
}