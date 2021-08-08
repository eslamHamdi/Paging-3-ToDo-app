package com.eslamhamdi.paging3_todo.ui.flow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eslamhamdi.paging3_todo.domain.Task
import com.eslamhamdi.paging3_todo.repository.flow.TaskFlowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(private val dataSource: TaskFlowRepository):ViewModel() {


    fun getPagingData(): Flow<PagingData<Task>>
    {
        return dataSource.getTaskList()
            .cachedIn(viewModelScope)

    }


}