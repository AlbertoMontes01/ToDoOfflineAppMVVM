package com.example.todoofflineappmvvm.data.local

import com.example.todoofflineappmvvm.data.local.dao.ToDoDao
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: ToDoDao
) {
    suspend fun getTodos() = dao.getAllToDo()
    suspend fun saveTodos(todos: List<ToDoEntity>) = dao.insertAllToDo(todos)
    suspend fun updateTodo(todo: ToDoEntity) = dao.updateToDo(todo)
    suspend fun deleteTodos(ids: List<Int>) = dao.deleteByIds(ids)
}