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
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.eslamhamdi.paging3_todo.databinding.FragmentFlowRemoteMediatorBinding
import com.eslamhamdi.paging3_todo.showErrorSnackBar
import com.eslamhamdi.paging3_todo.ui.adapter.TaskLoadStateAdabter
import com.eslamhamdi.paging3_todo.ui.adapter.TaskPagingDataAdapter
import com.eslamhamdi.paging3_todo.ui.flow.viewmodel.FlowRemoteMediatorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class FlowRemoteMediatorFragment: Fragment() , TaskLoadStateAdabter.RetryClickListener {
    private lateinit var binding: FragmentFlowRemoteMediatorBinding
    lateinit var pagingDataAdapter: TaskPagingDataAdapter
    val viewModel: FlowRemoteMediatorViewModel by viewModels()

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

        pagingDataAdapter = TaskPagingDataAdapter()
        binding.rvFlowRemotePaging.setHasFixedSize(true)

        binding.rvFlowRemotePaging.adapter = pagingDataAdapter.withLoadStateHeaderAndFooter(header = TaskLoadStateAdabter().also {
            it.retryListener = this
        } , footer = TaskLoadStateAdabter().also {
            it.retryListener = this})





        pagingDataAdapter.addLoadStateListener {
            (it.source.refresh is LoadState.Loading).also { state ->
                binding.flowRemoteProgress.isVisible = state


            }


            //load State for error and show error msg
            val errorState = it.source.refresh as? LoadState.Error ?:
            it.source.prepend as? LoadState.Error ?:
            it.source.append as? LoadState.Error ?:
            it.refresh as? LoadState.Error ?:
            it.append as? LoadState.Error ?:
            it.prepend as? LoadState.Error
            errorState?.let { loadError ->


                showErrorSnackBar(loadError.error.message.toString(), requireView())
            }

        }

        observers()
    }


    fun observers() {
        lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.getPagingTasks().collectLatest {
                    pagingDataAdapter.submitData(it)
                }
            }
        }

    }

    override fun onClick() {
       observers()
    }
}