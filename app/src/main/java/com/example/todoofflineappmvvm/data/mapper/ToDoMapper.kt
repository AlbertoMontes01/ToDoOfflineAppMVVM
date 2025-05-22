package com.example.todoofflineappmvvm.data.mapper

import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.example.todoofflineappmvvm.data.remote.model.ToDo

fun ToDo.toEntity() = ToDoEntity(
    id = this.id,
    userId = this.userId,
    title = this.title,
    completed = this.completed
)

fun ToDoEntity.toDo() = ToDo(
    userId = this.userId,
    id = this.id,
    title = this.title,
    completed = this.completed
)