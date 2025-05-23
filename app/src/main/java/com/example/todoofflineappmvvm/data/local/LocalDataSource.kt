package com.example.todoofflineappmvvm.data.local

import android.util.Log
import com.example.todoofflineappmvvm.data.local.dao.ToDoDao
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: ToDoDao
) {
    fun observeTodos(): Flow<List<ToDoEntity>> = dao.getAllToDo().also {
        Log.d("ToDoRepository", "Observing todos from Room")
    }
    suspend fun saveTodos(todos: List<ToDoEntity>) {
        dao.clearAll()
        dao.insertAllToDo(todos)
    }
    suspend fun getTodosDirectly(): List<ToDoEntity> = dao.getTodosDirectly()

    suspend fun updateTodo(todo: ToDoEntity) = dao.updateToDo(todo)
    suspend fun deleteTodos(ids: List<Int>) = dao.deleteByIds(ids)
    suspend fun insertTodo(todo: ToDoEntity) = dao.insertToDo(todo)

}
