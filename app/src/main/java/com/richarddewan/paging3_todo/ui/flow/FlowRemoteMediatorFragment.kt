package com.richarddewan.paging3_todo.ui.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.ExperimentalPagingApi
import com.richarddewan.paging3_todo.databinding.FragmentFlowPagingSourceBinding
import com.richarddewan.paging3_todo.databinding.FragmentFlowRemoteMediatorBinding
import com.richarddewan.paging3_todo.ui.adapter.TaskPagingDataAdapter
import com.richarddewan.paging3_todo.ui.flow.viewmodel.FlowRemoteMediatorViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagingApi
@AndroidEntryPoint
class FlowRemoteMediatorFragment: Fragment() {
    private lateinit var binding: FragmentFlowRemoteMediatorBinding
    lateinit var pagingDataAdapter: TaskPagingDataAdapter
    val viewModel:FlowRemoteMediatorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowRemoteMediatorBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}