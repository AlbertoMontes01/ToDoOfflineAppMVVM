package com.example.todoofflineappmvvm.ui.todo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoofflineappmvvm.data.local.entity.ToDoEntity
import com.example.todoofflineappmvvm.databinding.ItemTodoBinding

class ToDoAdapter(
    private val onCheckChanged: (ToDoEntity) -> Unit,
    private val onDeleteClicked: (ToDoEntity) -> Unit,
    private val isInDeleteMode: () -> Boolean,
    private val onItemClick: (ToDoEntity) -> Unit = {},
    private val onItemLongClick: (ToDoEntity) -> Unit = {}
) : ListAdapter<ToDoEntity, ToDoAdapter.ToDoViewHolder>(DiffCallback) {

    private val selectedIds = mutableSetOf<Int>()

    fun getSelectedIds(): List<Int> = selectedIds.toList()

    fun clearSelection() {
        selectedIds.clear()
        notifyDataSetChanged()
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ToDoEntity>() {
            override fun areItemsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ToDoEntity, newItem: ToDoEntity): Boolean =
                oldItem == newItem
        }
    }

    inner class ToDoViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: ToDoEntity) {
            val inDeleteMode = isInDeleteMode()
            val isSelected = selectedIds.contains(todo.id)

            binding.textTitle.text = todo.title

            if (inDeleteMode) {
                binding.checkCompleted.visibility = View.GONE
                binding.btnDelete.visibility = View.GONE

                binding.root.setBackgroundColor(
                    if (isSelected) 0xFFE57373.toInt() else 0xFFFFFFFF.toInt() // rojo claro si está seleccionado
                )

                binding.root.setOnClickListener {
                    if (isSelected) selectedIds.remove(todo.id)
                    else selectedIds.add(todo.id)
                    notifyItemChanged(adapterPosition)
                }
            } else {
                binding.checkCompleted.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.VISIBLE
                binding.root.setBackgroundColor(0xFFFFFFFF.toInt())

                binding.checkCompleted.setOnCheckedChangeListener(null)
                binding.checkCompleted.isChecked = todo.completed
                binding.checkCompleted.setOnCheckedChangeListener { _, isChecked ->
                    onCheckChanged(todo.copy(completed = isChecked))
                }

                binding.btnDelete.setOnClickListener {
                    onDeleteClicked(todo)
                }

                binding.root.setOnClickListener { onItemClick(todo) }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
