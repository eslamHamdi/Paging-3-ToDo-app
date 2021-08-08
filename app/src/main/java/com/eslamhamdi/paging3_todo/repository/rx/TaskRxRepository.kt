package com.eslamhamdi.paging3_todo.repository.rx

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.RxPagingSource
import androidx.paging.rxjava3.flowable
import com.eslamhamdi.paging3_todo.domain.Task
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class TaskRxRepository @Inject constructor(private val pagingSource: RxPagingSource<Int, Task>) {

    @ExperimentalCoroutinesApi
    fun getTaskList():Flowable<PagingData<Task>>
    {
        return  Pager(defaultPageConfig(),pagingSourceFactory = {pagingSource}).flowable
    }

    private fun defaultPageConfig(): PagingConfig
    {
        return PagingConfig(pageSize = 10,prefetchDistance = 10,
            enablePlaceholders = true,initialLoadSize = 30,maxSize = 40)
    }


}