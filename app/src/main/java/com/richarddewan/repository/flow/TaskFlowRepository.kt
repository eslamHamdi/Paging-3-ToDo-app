package com.richarddewan.repository.flow

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.richarddewan.domain.Task
import com.richarddewan.repository.paging.TaskFlowPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TaskFlowRepository @Inject constructor(private val source:TaskFlowPagingSource) {

    fun getTaskList():Flow<PagingData<Task>>
    {
        return Pager(defaultPageConfig(),pagingSourceFactory = {source}).flow
            .flowOn(Dispatchers.IO)
    }

    private fun defaultPageConfig():PagingConfig
    {
        return PagingConfig(pageSize = 10,prefetchDistance = 10,
        enablePlaceholders = true,initialLoadSize = 30,maxSize = 40)
    }
}