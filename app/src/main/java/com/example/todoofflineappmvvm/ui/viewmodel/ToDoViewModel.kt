package com.example.todoofflineappmvvm.ui.todo.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoofflineappmvvm.data.FilterType
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.example.todoofflineappmvvm.domain.ToDoRepository
import com.example.todoofflineappmvvm.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {
    private val _showInsertMessage = MutableSharedFlow<String>()
    val showInsertMessage: SharedFlow<String> = _showInsertMessage
    private val _todos = MutableStateFlow<List<ToDoEntity>>(emptyList())

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading
    private val _filterType = MutableStateFlow(FilterType.ALL)


    init {
        observeTodos()
    }

    private fun observeTodos() {
        viewModelScope.launch {
            repository.observeTodos().collect { list ->
                _todos.value = list
            }
        }
    }

    fun setFilter(type: FilterType) {
        _filterType.value = type
    }

    val filteredTodos: StateFlow<List<ToDoEntity>> =
        combine(_todos, _filterType) { todosList, filter ->
            when (filter) {
                FilterType.ALL -> todosList
                FilterType.COMPLETED -> todosList.filter { it.completed }
                FilterType.PENDING -> todosList.filter { !it.completed }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun loadTodos(context: Context, forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val localData = repository.getLocalTodos()
                val shouldFetchFromApi = (localData.isEmpty() && NetworkUtils.isInternetAvailable(context)) || forceRefresh

                if (shouldFetchFromApi) {
                    repository.refreshTodosFromApi()
                    _showInsertMessage.emit("Fetched latest tasks from server")
                } else {
                    _todos.value = localData
                }
            } catch (e: Exception) {
                Log.e("ToDoViewModel", "Error al cargar todos", e)
            } finally {
                _loading.value = false
            }
        }
    }





    fun updateTodo(todo: ToDoEntity) {
        viewModelScope.launch {
            repository.update(todo)
            // actualiza lista local para reflejar el cambio de inmediato
            val updatedList = _todos.value.map {
                if (it.id == todo.id) todo else it
            }
            _todos.value = updatedList
        }
    }

    fun deleteTodos(ids: List<Int>) {
        viewModelScope.launch {
            repository.delete(ids)
            _showInsertMessage.emit("Deleted task, id: $ids")
        }
    }

    fun insertTodo(title: String, completed: Boolean) {
        val userId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
        val id = UUID.randomUUID().hashCode().absoluteValue
        viewModelScope.launch {
            val newTodo = ToDoEntity(
                id = id,
                title = title,
                completed = completed,
                userId = userId
            )
            repository.insert(newTodo)
            _showInsertMessage.emit("Task created successfully")
        }
    }

}

