package com.example.todoofflineappmvvm.domain

import com.example.todoofflineappmvvm.data.local.LocalDataSource
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.example.todoofflineappmvvm.data.mapper.toEntity
import com.example.todoofflineappmvvm.data.remote.RemoteDataSource
import javax.inject.Inject

class ToDoRepository @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) {
    suspend fun getTodos(forceRefresh: Boolean): List<ToDoEntity> {
        return if (forceRefresh) {
            val todos = remote.fetchTodos().map { it.toEntity() }
            local.saveTodos(todos)
            todos
        } else {
            local.getTodos()
        }
    }

    suspend fun update(todo: ToDoEntity) = local.updateTodo(todo)
    suspend fun delete(ids: List<Int>) = local.deleteTodos(ids)
}