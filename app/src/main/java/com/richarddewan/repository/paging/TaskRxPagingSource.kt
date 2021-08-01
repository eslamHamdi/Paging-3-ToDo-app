package com.richarddewan.repository.paging

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.richarddewan.domain.Task
import com.richarddewan.domain.TaskResponseToTaskPaging
import com.richarddewan.paging3_todo.data.remote.ToDoService
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TaskRxPagingSource @Inject constructor( private val service: ToDoService):
    RxPagingSource<Int, Task>()
{
    override fun getRefreshKey(state: PagingState<Int, Task>): Int? {
        return state.anchorPosition
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Task>> {

        val currentPage = params.key ?: 1


        return  service.getTaskListRx(currentPage)

            .subscribeOn(Schedulers.io())

            .map {


                  val data =  TaskResponseToTaskPaging(it)?.tasks
             val   result: LoadResult<Int,Task> =  LoadResult.Page(data = data!!,prevKey = if (currentPage == 1) null else currentPage -1,
                    nextKey = if (currentPage == data.size) null else currentPage+1)

                result

            }
            .onErrorReturn {
                LoadResult.Error(it) }


    }
}