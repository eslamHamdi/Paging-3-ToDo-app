package com.richarddewan.paging3_todo.ui.flow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.richarddewan.domain.Task
import com.richarddewan.repository.flow.TaskFlowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(private val dataSource:TaskFlowRepository):ViewModel() {


    fun getPagingData(): Flow<PagingData<Task>>
    {
        return dataSource.getTaskList()
            .cachedIn(viewModelScope)

    }


}