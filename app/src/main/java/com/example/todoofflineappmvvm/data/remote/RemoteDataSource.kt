package com.example.todoofflineappmvvm.data.remote

import android.util.Log
import com.example.todoofflineappmvvm.data.remote.api.ToDoApiService
import com.example.todoofflineappmvvm.data.remote.model.ToDo
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: ToDoApiService
) {
    suspend fun fetchTodos(): List<ToDo> {
        return try {
            val result = api.getAllToDo()
            Log.d("RemoteDataSource", "Fetched ${result.size} todos from API")
            Log.d("RemoteDataSource", "Fetched ${result} todos from API")
            result
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Fetch failed", e)
            emptyList()
        }
    }
}
