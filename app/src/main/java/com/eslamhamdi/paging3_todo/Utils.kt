package com.eslamhamdi.paging3_todo

import android.view.View
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity
import com.eslamhamdi.paging3_todo.data.remote.response.DataItem
import com.eslamhamdi.paging3_todo.data.remote.response.TaskResponse
import com.eslamhamdi.paging3_todo.domain.Task
import com.eslamhamdi.paging3_todo.domain.TasksPaging
import com.google.android.material.snackbar.Snackbar

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

fun List<DataItem?>?.toEntities(): List<TaskEntity>?
{

    return this?.map {

        TaskEntity(note = it?.note,updatedAt = it?.updatedAt,userId = it?.userId,createdAt = it?.createdAt
            ,id = it?.id?.toInt(),title = it?.title,body = it?.body,status = it?.status)
    }
}


fun List<TaskEntity>?.toDomain(): List<Task>?
{

    return this?.map {

        Task(note = it?.note,updatedAt = it?.updatedAt,userId = it?.userId,createdAt = it?.createdAt
            ,id = it?.id.toString(),title = it?.title,body = it?.body,status = it?.status)
    }
}

fun showErrorSnackBar(msg:String?,view: View)
{
    Snackbar.make(view,msg!!, Snackbar.LENGTH_SHORT).show()
}




