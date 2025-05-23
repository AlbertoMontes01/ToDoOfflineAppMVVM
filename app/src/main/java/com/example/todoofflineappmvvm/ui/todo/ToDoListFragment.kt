package com.example.todoofflineappmvvm.ui.todo

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoofflineappmvvm.R
import com.example.todoofflineappmvvm.databinding.FragmentToDoListBinding
import com.example.todoofflineappmvvm.ui.todo.adapter.ToDoAdapter
import com.example.todoofflineappmvvm.ui.todo.viewmodel.ToDoViewModel
import com.example.todoofflineappmvvm.utils.ConnectionLiveDataUtil
import com.example.todoofflineappmvvm.utils.NetworkUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoListFragment : Fragment() {

    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!
    private var deleteMode = false
    private val viewModel: ToDoViewModel by viewModels()
    private lateinit var adapter: ToDoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupAdapter() {
        adapter = ToDoAdapter(
            onCheckChanged = { viewModel.updateTodo(it) },
            onDeleteClicked = { viewModel.deleteTodos(listOf(it.id)) },
            isInDeleteMode = { deleteMode }
        )

        binding.recyclerViewTodos.adapter = adapter
        binding.recyclerViewTodos.layoutManager = LinearLayoutManager(requireContext())
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()

        ConnectionLiveDataUtil(requireContext()).observe(viewLifecycleOwner) { isConnected ->
            binding.textOfflineBanner.visibility = if (isConnected) View.GONE else View.VISIBLE
        }

        viewModel.loadTodos(
            requireContext(),
            forceRefresh = NetworkUtils.isInternetAvailable(requireContext())
        )

        lifecycleScope.launch {
            viewModel.showInsertMessage.collectLatest { msg ->
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerViewTodos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTodos.adapter = adapter



        lifecycleScope.launch {
            viewModel.filteredTodos.collectLatest { list ->
                adapter.submitList(list)
                binding.textEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            }
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            val isOnline = NetworkUtils.isInternetAvailable(requireContext())
            if (isOnline) {
                viewModel.loadTodos(requireContext(), forceRefresh = true)
            } else {
                binding.textOfflineBanner.visibility = View.VISIBLE
                binding.swipeRefreshLayout.isRefreshing = false
                Toast.makeText(
                    requireContext(),
                    "Sin conexión. No se puede refrescar.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnToggleDeleteMode.setOnClickListener {
            if (deleteMode) {
                val idsToDelete = adapter.getSelectedIds()
                if (idsToDelete.isNotEmpty()) {
                    viewModel.deleteTodos(idsToDelete)
                    adapter.clearSelection()
                }
            }
            deleteMode = !deleteMode
            adapter.notifyDataSetChanged()
        }





        binding.fabAddTodo.setOnClickListener {
            val dialogView = LayoutInflater.from(requireContext())
                .inflate(R.layout.layout_bottom_sheet_add_todo, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(dialogView)

            val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
            val completedCheckBox = dialogView.findViewById<CheckBox>(R.id.checkBoxCompleted)
            val saveButton = dialogView.findViewById<Button>(R.id.buttonSave)

            saveButton.setOnClickListener {
                val title = titleEditText.text.toString()
                val completed = completedCheckBox.isChecked

                if (title.isNotBlank()) {
                    viewModel.insertTodo(title, completed)
                    Toast.makeText(requireContext(), "Task created!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Title is required", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show()
        }

        lifecycleScope.launch {
            viewModel.loading.collectLatest { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.swipeRefreshLayout.isRefreshing = isLoading
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}