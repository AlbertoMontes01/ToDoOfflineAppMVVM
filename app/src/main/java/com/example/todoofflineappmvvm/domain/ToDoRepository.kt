package com.example.todoofflineappmvvm.domain

import com.example.todoofflineappmvvm.data.local.LocalDataSource
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.example.todoofflineappmvvm.data.mapper.toEntity
import com.example.todoofflineappmvvm.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import android.util.Log
import javax.inject.Inject

class ToDoRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) {
    fun observeTodos(): Flow<List<ToDoEntity>> = local.observeTodos()

    suspend fun refreshTodosFromApi() {
        try {
            val todos = remote.fetchTodos().map { it.toEntity() }
            Log.d("ToDoRepository", "Fetched ${todos.size} todos from API")
            local.saveTodos(todos)
            Log.d("ToDoRepository", "Todos saved in Room")
        } catch (e: Exception) {
            Log.e("ToDoRepository", "API fetch failed: ${e.message}")
        }
    }
    suspend fun insert(todo: ToDoEntity) = local.insertTodo(todo)
    suspend fun getLocalTodos(): List<ToDoEntity> = local.getTodosDirectly()


    suspend fun update(todo: ToDoEntity) = local.updateTodo(todo)
    suspend fun delete(ids: List<Int>) = local.deleteTodos(ids)
}