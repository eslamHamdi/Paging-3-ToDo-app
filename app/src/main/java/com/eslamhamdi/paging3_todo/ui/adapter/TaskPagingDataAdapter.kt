package com.eslamhamdi.paging3_todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eslamhamdi.paging3_todo.databinding.LoadStateFooterViewItemBinding
import com.eslamhamdi.paging3_todo.domain.Task
import com.eslamhamdi.paging3_todo.databinding.TaskListViewBinding

class TaskPagingDataAdapter:PagingDataAdapter<Task,TaskPagingDataAdapter.TaskViewHolder
        >(DiffCallBack) {


             class TaskViewHolder(val binding:TaskListViewBinding ):RecyclerView.ViewHolder(binding.root)
            {
                fun onBind(task: Task)
                {
                    binding.lbBody.text = task.body
                    binding.lbNote.text = task.note
                    binding.lbTaskId.text = task.id
                    binding.lbUserId.text = task.userId
                    binding.lbStatus.text = task.status
                    binding.lbTitle.text = task.title
                }

            }

    inner class LoadStateViewHolder(val binding:LoadStateFooterViewItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(loadState: LoadState)
        {
            binding.retryButton.setOnClickListener {
                retryListener?.onClick()
            }

            when(loadState)
            {
                is LoadState.Loading ->{
                    binding.errorMsg.isVisible = false
                    binding.progressBar.isVisible = true
                    binding.retryButton.isVisible = false

                }

                is LoadState.NotLoading ->{
                    binding.errorMsg.isVisible = false
                    binding.progressBar.isVisible = false
                    binding.retryButton.isVisible = false
                }

                is LoadState.Error ->{
                    binding.errorMsg.isVisible = true
                    binding.progressBar.isVisible = false
                    binding.retryButton.isVisible = true
                    binding.errorMsg.text = loadState.error.message
                }
            }


        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
       getItem(position)?.let {
           holder.onBind(it)
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskListViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return TaskViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }


    companion object DiffCallBack: DiffUtil.ItemCallback<Task>()
    {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {

            return oldItem == newItem
        }

    }

    var retryListener:RetryClickListener? =null

    interface RetryClickListener
    {
        fun onClick()
    }
}