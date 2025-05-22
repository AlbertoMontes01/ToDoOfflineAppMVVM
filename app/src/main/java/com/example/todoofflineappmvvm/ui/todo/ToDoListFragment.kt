package com.example.todoofflineappmvvm.ui.todo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoofflineappmvvm.R
import com.example.todoofflineappmvvm.databinding.FragmentToDoListBinding
import com.example.todoofflineappmvvm.ui.todo.adapter.ToDoAdapter
import com.example.todoofflineappmvvm.ui.todo.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoListFragment : Fragment() {

    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ToDoViewModel by viewModels()
    private lateinit var adapter: ToDoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ToDoAdapter { updatedTodo ->
            viewModel.updateTodo(updatedTodo)
        }

        binding.recyclerViewTodos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTodos.adapter = adapter

        // Cargar datos (desde caché si no hay conexión)
        viewModel.loadTodos()

        // Observadores
        lifecycleScope.launch {
            viewModel.todos.collectLatest {
                adapter.submitList(it)
                binding.textEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.loading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }

        // TODO: Opcional: manejar errores y conexión
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}