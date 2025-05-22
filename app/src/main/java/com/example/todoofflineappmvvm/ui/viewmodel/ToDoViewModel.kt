package com.example.todoofflineappmvvm.ui.todo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.example.todoofflineappmvvm.domain.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    private val _todos = MutableStateFlow<List<ToDoEntity>>(emptyList())
    val todos: StateFlow<List<ToDoEntity>> = _todos

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loadTodos(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getTodos(forceRefresh)
            _todos.value = result
            _loading.value = false
        }
    }

    fun updateTodo(todo: ToDoEntity) {
        viewModelScope.launch {
            repository.update(todo)
            loadTodos() // refresh local
        }
    }

    fun deleteTodos(ids: List<Int>) {
        viewModelScope.launch {
            repository.delete(ids)
            loadTodos()
        }
    }
}