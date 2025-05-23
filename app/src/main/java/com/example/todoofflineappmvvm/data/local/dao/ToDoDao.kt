package com.example.todoofflineappmvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo")
    fun getAllToDo(): Flow<List<ToDoEntity>>
    @Query("SELECT * FROM todo")
    suspend fun getTodosDirectly(): List<ToDoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllToDo(todos: List<ToDoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(todo: ToDoEntity)

    @Update
    suspend fun updateToDo(todo: ToDoEntity)

    @Query("DELETE FROM todo WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Int>)

    @Query("DELETE FROM todo")
    suspend fun clearAll()
}
