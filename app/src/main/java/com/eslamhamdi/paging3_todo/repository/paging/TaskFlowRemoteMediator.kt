package com.eslamhamdi.paging3_todo.repository.paging

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.eslamhamdi.paging3_todo.data.local.database.DataBaseService
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity
import com.eslamhamdi.paging3_todo.data.local.entity.TaskKeyEntity
import com.eslamhamdi.paging3_todo.data.remote.ToDoService
import com.eslamhamdi.paging3_todo.domain.Task
import com.eslamhamdi.paging3_todo.toEntities
import java.io.InvalidObjectException
import javax.inject.Inject

@ExperimentalPagingApi
class TaskFlowRemoteMediator @Inject constructor(private val remoteService: ToDoService,private val localDataBaseService: DataBaseService):
RemoteMediator<Int, TaskEntity>() {

    @SuppressLint("LogNotTimber")
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TaskEntity>
    ): MediatorResult {
        val page = when (loadType) {

            LoadType.REFRESH ->

           {

                            val key = getKeyForClosestCurrentItemPosition(state)
              key?.nextKey?.minus(1)  ?: 1


          }





            LoadType.PREPEND -> {
                val key = getKeyForTheFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("first item is empty"))

                val previousKey = key.prevKey ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                        previousKey

                //return MediatorResult.Success(true)


                }

            LoadType.APPEND -> {
                 val key = getKeyForTheLastItem(state)
                     ?: return MediatorResult.Error(InvalidObjectException("last item is empty"))
                val nextKey = key.nextKey ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                        nextKey
                    }


            }

        try {
            val response = page.let { remoteService.getTaskList(it!!) }





            localDataBaseService.withTransaction {
                if (loadType == LoadType.REFRESH)
                {
                    localDataBaseService.getFlowDao().wipeTasks()
                    localDataBaseService.getKeyFlowDao().wipeKeys()
                }
                val data = response.data.toEntities()
                val keys = response.data?.map {

                    TaskKeyEntity(taskId = it?.id!!.toInt(),prevKey = if (page == 1) null else page!!-1,
                        nextKey = if (page == response.lastPage) null else page!!+1 )
                }

                data?.let { localDataBaseService.getFlowDao().insertTasks(it) }
                keys?.let { localDataBaseService.getKeyFlowDao().insertKeys(keys) }
            }





            return MediatorResult.Success(
                endOfPaginationReached = response.data?.size!! < 10
            )

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }


    }


    private suspend fun getKeyForTheFirstItem(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
        return localDataBaseService.withTransaction { localDataBaseService.getKeyFlowDao().getKeys().firstOrNull() }

//        return state.firstItemOrNull()?.let { taskEntity ->
//
//            localDataBaseService.getKeyFlowDao().getKey(taskEntity.id)
//
//        }


//     return   state.pages.firstOrNull() { it.data.isNotEmpty() }
//            ?.data?.firstOrNull()
//            ?.let { taskEntity ->
//                localDataBaseService.getKeyFlowDao().getKey(taskEntity.id)
//            }
        }


    private suspend fun getKeyForTheLastItem(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
       return localDataBaseService.withTransaction{localDataBaseService.getKeyFlowDao().getKeys().lastOrNull()}

//        return state.lastItemOrNull()?.let { taskEntity ->
//
//            localDataBaseService.withTransaction {localDataBaseService.getKeyFlowDao().getKey(taskEntity.id)  }
//        }

//             return   state.pages.lastOrNull() { it.data.isNotEmpty() }
//           ?.data?.lastOrNull()
//          ?.let { taskEntity ->
//               localDataBaseService.withTransaction { localDataBaseService.getKeyFlowDao().getKey(taskEntity.id) }
//           }


    }

    private suspend fun getKeyForClosestCurrentItemPosition(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
        return state.anchorPosition?.let {
             state.closestItemToPosition(it)?.id?.let { id->

              localDataBaseService.withTransaction { localDataBaseService.getKeyFlowDao().getKey(id) }

            }

        }
    }
}