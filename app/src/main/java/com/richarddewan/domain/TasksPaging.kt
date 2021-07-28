package com.richarddewan.domain

import com.google.gson.annotations.SerializedName
import com.richarddewan.paging3_todo.data.remote.response.DataItem
import com.richarddewan.paging3_todo.data.remote.response.TaskResponse

data class TasksPaging(
    val currentPage:Int = 0,
    val totalPages:Int =0,
    val tasks: List<Task>
)

data class Task(

    val note: String? = null,

    val updatedAt: String? = null,

    val userId: String? = null,

    val createdAt: String? = null,

    val id: String? = null,

    val title: String? = null,

    val body: String? = null,

    val status: String? = null
)


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

