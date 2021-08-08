package com.eslamhamdi.paging3_todo.repository.flow

import androidx.paging.*
import com.eslamhamdi.paging3_todo.data.local.database.DataBaseService
import com.eslamhamdi.paging3_todo.data.local.database.TaskFlowDao
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity
import com.eslamhamdi.paging3_todo.repository.paging.TaskFlowRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
@ExperimentalPagingApi
class FlowRemoteMediatorRepository @Inject constructor(private val source:TaskFlowRemoteMediator,private val localeData: TaskFlowDao)
{

    fun getTasksList(): Flow<PagingData<TaskEntity>>
    {
        return Pager(config =defaultPageConfig() ,remoteMediator = source,pagingSourceFactory = {

            localeData.getTasks()

        } ).flow.flowOn(Dispatchers.IO)

    }





    private fun defaultPageConfig(): PagingConfig
    {
        return PagingConfig(pageSize = 10,prefetchDistance = 10,
            enablePlaceholders = true,initialLoadSize = 30,maxSize = 40)
    }
}