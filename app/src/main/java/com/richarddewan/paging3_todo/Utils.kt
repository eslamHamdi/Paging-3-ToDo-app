package com.richarddewan.paging3_todo

import com.richarddewan.paging3_todo.data.local.entity.TaskEntity
import com.richarddewan.paging3_todo.data.remote.response.DataItem
import com.richarddewan.paging3_todo.data.remote.response.TaskResponse
import com.richarddewan.paging3_todo.domain.Task
import com.richarddewan.paging3_todo.domain.TasksPaging

fun List<DataItem?>?.toTasks(): List<Task>?
{
    return this?.map {

        Task(note = it?.note,updatedAt = it?.updatedAt,userId = it?.userId,createdAt = it?.createdAt
            ,id = it?.id,title = it?.title,body = it?.body,status = it?.status)
    }
}

fun TaskResponseToTaskPaging(taskResponse: TaskResponse): TasksPaging?
{
    return taskResponse.data.toTasks()?.let {
        TasksPaging(currentPage = taskResponse.currentPage!!,totalPages = taskResponse.total!!,
            tasks = it
        )
    }
}

fun List<DataItem?>?.toEntities(currentPage:Int?,lastPage:Int?): List<TaskEntity>?
{

    return this?.map {

        TaskEntity(note = it?.note,updatedAt = it?.updatedAt,userId = it?.userId,createdAt = it?.createdAt
            ,id = it?.id?.toLong(),title = it?.title,body = it?.body,status = it?.status,prevKey = if (currentPage == 1) null else currentPage!! -1,
            nextKey = if (currentPage == lastPage) null else currentPage+1 )
    }
}


fun List<TaskEntity>?.toDomain(): List<Task>?
{

    return this?.map {

        Task(note = it?.note,updatedAt = it?.updatedAt,userId = it?.userId,createdAt = it?.createdAt
            ,id = it?.id.toString(),title = it?.title,body = it?.body,status = it?.status)
    }
}




