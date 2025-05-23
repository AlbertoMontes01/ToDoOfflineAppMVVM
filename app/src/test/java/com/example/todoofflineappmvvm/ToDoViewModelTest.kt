package com.example.todoofflineappmvvm

import android.content.Context
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.example.todoofflineappmvvm.domain.ToDoRepository
import com.example.todoofflineappmvvm.ui.todo.viewmodel.ToDoViewModel
import com.example.todoofflineappmvvm.utils.NetworkUtils
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ToDoViewModelTest {

    private lateinit var viewModel: ToDoViewModel
    private val repository = mockk<ToDoRepository>(relaxed = true)
    private val context = mockk<Context>(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ToDoViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTodos should load from API if Room is empty and device is online`() = runTest {
        coEvery { repository.getLocalTodos() } returns emptyList()
        every { NetworkUtils.isInternetAvailable(context) } returns true
        coEvery { repository.refreshTodosFromApi() } just Runs

        viewModel.loadTodos(context)

        advanceUntilIdle()

        coVerify(exactly = 1) { repository.refreshTodosFromApi() }
    }
}
