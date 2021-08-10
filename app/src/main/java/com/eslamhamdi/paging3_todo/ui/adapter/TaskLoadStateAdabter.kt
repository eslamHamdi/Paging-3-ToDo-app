package com.eslamhamdi.paging3_todo.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eslamhamdi.paging3_todo.databinding.LoadStateFooterViewItemBinding

class TaskLoadStateAdabter:LoadStateAdapter<TaskLoadStateAdabter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(val binding: LoadStateFooterViewItemBinding): RecyclerView.ViewHolder(binding.root)
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
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = LoadStateFooterViewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return LoadStateViewHolder(binding)
    }

    var retryListener:RetryClickListener? =null

    interface RetryClickListener
    {
        fun onClick()
    }
}