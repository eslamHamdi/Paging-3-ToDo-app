package com.eslamhamdi.paging3_todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity
import com.eslamhamdi.paging3_todo.databinding.TaskListViewBinding
import com.eslamhamdi.paging3_todo.domain.Task

class TaskPagingDataAdapter:PagingDataAdapter<TaskEntity,TaskPagingDataAdapter.TaskViewHolder
        >(DiffCallBack) {


             class TaskViewHolder(val binding:TaskListViewBinding ):RecyclerView.ViewHolder(binding.root)
            {
                fun onBind(task: TaskEntity)
                {
                    binding.lbBody.text = task.body
                    binding.lbNote.text = task.note
                    binding.lbTaskId.text = task.id.toString()
                    binding.lbUserId.text = task.userId
                    binding.lbStatus.text = task.status
                    binding.lbTitle.text = task.title
                }

            }



    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

                getItem(position)?.let {
                    holder.onBind(it)
                }

       }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskPagingDataAdapter.TaskViewHolder {

        val binding = TaskListViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)


    }



    companion object DiffCallBack: DiffUtil.ItemCallback<TaskEntity>()
    {
        override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {

            return oldItem == newItem
        }

    }


}