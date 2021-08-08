package com.eslamhamdi.paging3_todo.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.eslamhamdi.paging3_todo.TaskResponseToTaskPaging
import com.eslamhamdi.paging3_todo.data.remote.ToDoService
import com.eslamhamdi.paging3_todo.domain.Task
import javax.inject.Inject

class TaskFlowPagingSource @Inject constructor( private val service: ToDoService):PagingSource<Int, Task>() {
    override fun getRefreshKey(state: PagingState<Int, Task>): Int? {

        return  state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Task> {
        val currentPage = params.key ?: 1

        return try {

            service.getTaskList(currentPage).let {

                val data = TaskResponseToTaskPaging(it)?.tasks
                LoadResult.Page(data = data!!,prevKey = if (currentPage == 1) null else currentPage -1,
                nextKey = if (currentPage == it.lastPage) null else currentPage+1)
            }

        }catch (e:Exception)
        {

            LoadResult.Error(e)
        }
    }
}