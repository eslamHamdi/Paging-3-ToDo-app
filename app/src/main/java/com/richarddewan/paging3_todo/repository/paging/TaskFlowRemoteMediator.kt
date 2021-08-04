package com.richarddewan.paging3_todo.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.richarddewan.paging3_todo.data.local.database.DataBaseService
import com.richarddewan.paging3_todo.data.local.entity.TaskEntity
import com.richarddewan.paging3_todo.data.remote.ToDoService
import com.richarddewan.paging3_todo.toEntities
import java.io.InvalidObjectException
import javax.inject.Inject

@ExperimentalPagingApi
class TaskFlowRemoteMediator @Inject constructor(private val remoteService: ToDoService,private val localDataBaseService: DataBaseService):
RemoteMediator<Int,TaskEntity>()
{

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TaskEntity>
    ): MediatorResult {
        val page = when (loadType) {

            LoadType.REFRESH -> {
                state.anchorPosition?.let {
                    state.closestItemToPosition(it)?.let { taskEntity->

                        taskEntity.nextKey?.minus(1) ?: 1
                    }

                }
            }


            LoadType.PREPEND -> {
                state.pages.firstOrNull()?.let {
                    it.data.firstOrNull().let { taskEntity ->

                        if (taskEntity == null) return MediatorResult.Error(InvalidObjectException("first item is empty"))
                        val previousKey = taskEntity.prevKey ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                        previousKey
                    }

                }
            }
            LoadType.APPEND -> {
                state.pages.lastOrNull()?.let {
                    it.data.lastOrNull().let { taskEntity ->
                        if (taskEntity == null) return MediatorResult.Error(InvalidObjectException("last item is empty"))
                        val nextKey = taskEntity.nextKey ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                        nextKey
                    }

                }
            }
        }

       try {
           localDataBaseService.withTransaction {
               if (loadType== LoadType.REFRESH)
                   localDataBaseService.getFlowDao().wipeTasks()
           }
           val response = page?.let { remoteService.getTaskList(it) }

           val currentPage = response?.currentPage
           val lastPage = response?.lastPage
           val data = response?.data.toEntities(currentPage,lastPage)

           data?.let { localDataBaseService.getFlowDao().insertTasks(it) }




           return MediatorResult.Success(
               endOfPaginationReached = response?.currentPage == response?.total )

       }

       catch (e:Exception)
       {
           return MediatorResult.Error(e)
       }



    }
    }
