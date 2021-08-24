package com.eslamhamdi.paging3_todo.ui.rx

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.eslamhamdi.paging3_todo.databinding.FragmentRxPagingSourceBinding
import com.eslamhamdi.paging3_todo.ui.adapter.SingleSourceDataAdapter
import com.eslamhamdi.paging3_todo.ui.adapter.TaskLoadStateAdabter
import com.eslamhamdi.paging3_todo.ui.adapter.TaskPagingDataAdapter
import com.eslamhamdi.paging3_todo.ui.rx.viewmodel.RxViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class RxPagingSourceFragment: Fragment(),TaskLoadStateAdabter.RetryClickListener {
    private lateinit var binding: FragmentRxPagingSourceBinding
    val viewModel:RxViewModel by viewModels()
    lateinit var pagingDataAdapter: SingleSourceDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRxPagingSourceBinding.inflate(layoutInflater)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pagingDataAdapter = SingleSourceDataAdapter()

        binding.rvRxPaging.adapter = pagingDataAdapter.withLoadStateHeaderAndFooter(header = TaskLoadStateAdabter().also {
            it.retryListener = this
        } , footer = TaskLoadStateAdabter().also {
            it.retryListener = this})

        pagingDataAdapter.addLoadStateListener {
            (it.source.refresh is LoadState.Loading).also {state->
                binding.rxProgress.isVisible = state }

            //load State for error and show error msg
            val errorState = it.source.refresh as? LoadState.Error ?:
            it.source.prepend as? LoadState.Error ?:
            it.source.append as? LoadState.Error ?:
            it.refresh as? LoadState.Error ?:
            it.append as? LoadState.Error ?:
            it.prepend as? LoadState.Error

            errorState?.let { LoadStateError->

                showErrorSnackBar(LoadStateError.error.localizedMessage ?: "Error")
            }

        }

        observer()
    }

    @ExperimentalCoroutinesApi
    fun observer()
    {
        viewModel.getPagingData().subscribe({
            pagingDataAdapter.submitData(lifecycle, it)
        }, {
            Log.d(null, "observer: ${it.localizedMessage} ")
        })





    }

    fun showErrorSnackBar(msg:String?)
    {
        Snackbar.make(requireView(),msg!!, Snackbar.LENGTH_SHORT).apply {
            setAction("close") { dismiss() }
        }.show()
    }

    @ExperimentalCoroutinesApi
    override fun onClick() {
        observer()
    }
}