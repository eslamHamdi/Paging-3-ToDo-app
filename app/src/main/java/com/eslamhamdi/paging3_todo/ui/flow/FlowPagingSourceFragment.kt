package com.eslamhamdi.paging3_todo.ui.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.eslamhamdi.paging3_todo.databinding.FragmentFlowPagingSourceBinding
import com.eslamhamdi.paging3_todo.showErrorSnackBar
import com.eslamhamdi.paging3_todo.ui.adapter.TaskLoadStateAdabter
import com.eslamhamdi.paging3_todo.ui.adapter.TaskPagingDataAdapter
import com.eslamhamdi.paging3_todo.ui.flow.viewmodel.FlowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch



@InternalCoroutinesApi
@AndroidEntryPoint
class FlowPagingSourceFragment: Fragment(),TaskLoadStateAdabter.RetryClickListener {
    private lateinit var binding: FragmentFlowPagingSourceBinding
    lateinit var pagingDataAdapter: TaskPagingDataAdapter

    val viewModel:FlowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlowPagingSourceBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagingDataAdapter = TaskPagingDataAdapter()


        binding.rvFlowPaging.adapter = pagingDataAdapter.withLoadStateHeaderAndFooter(header = TaskLoadStateAdabter().also {
            it.retryListener = this
        } , footer = TaskLoadStateAdabter().also {
            it.retryListener = this})


        pagingDataAdapter.addLoadStateListener {
            (it.source.refresh is LoadState.Loading).also { state->
                binding.flowProgress.isVisible = state }

            //load State for error and show error msg
            val errorState = it.source.refresh as? LoadState.Error ?:
            it.source.prepend as? LoadState.Error ?:
            it.source.append as? LoadState.Error ?:
            it.refresh as? LoadState.Error ?:
            it.append as? LoadState.Error ?:
            it.prepend as? LoadState.Error

            errorState?.let { loadError->

                showErrorSnackBar(loadError.error.message.toString(),requireView())
            }

        }



        observers()


    }


    fun observers()
    {
        lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.getPagingData().collect {
                    pagingDataAdapter.submitData(lifecycle,it)
                }
            }

        }
    }

    override fun onClick() {
        TODO("Not yet implemented")
    }


}