package com.example.todoofflineappmvvm.ui.todo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.example.todoofflineappmvvm.databinding.ItemTodoBinding

class ToDoAdapter(
    private val onCheckChanged: (ToDoEntity) -> Unit
) : ListAdapter<ToDoEntity, ToDoAdapter.ToDoViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ToDoEntity>() {
            override fun areItemsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ToDoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: ToDoEntity) {
            binding.apply {
                textTitle.text = todo.title
                checkCompleted.isChecked = todo.completed

                checkCompleted.setOnCheckedChangeListener(null) // evitar doble trigger
                checkCompleted.setOnCheckedChangeListener { _, isChecked ->
                    onCheckChanged(todo.copy(completed = isChecked))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }
}