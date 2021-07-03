package com.richarddewan.paging3_todo.data.remote

import com.richarddewan.Constants
import com.richarddewan.paging3_todo.data.remote.response.TaskResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ToDoService {

    @Headers(Constants.HEADER_ACCEPT)
    @GET("/all_task")
    suspend fun getTaskList(@Query("page") pageNum:Int):TaskResponse

    @Headers(Constants.HEADER_ACCEPT)
    @GET("/all_task")
     fun getTaskListRx(@Query("page") pageNum:Int):Single<TaskResponse>
}