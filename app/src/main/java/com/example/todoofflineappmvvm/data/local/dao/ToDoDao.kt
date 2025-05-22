package com.example.todoofflineappmvvm.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo")
    suspend fun getAllToDo(): List<ToDoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllToDo(todos: List<ToDoEntity>)

    @Update
    suspend fun updateToDo(todo: ToDoEntity)

    @Query("DELETE FROM todo WHERE id IN (:ids)")
    suspend fun deleteByIds(ids: List<Int>)
}
