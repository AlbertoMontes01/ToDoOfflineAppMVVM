package com.example.todoofflineappmvvm.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDoEntity(
    @PrimaryKey(autoGenerate = true)
    val localId: Int = 0,

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "userId")
    val userId: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "completed")
    val completed: Boolean
)
