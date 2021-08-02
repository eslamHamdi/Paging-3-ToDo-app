package com.richarddewan.paging3_todo.data.local.database

import androidx.paging.PagingSource
import androidx.room.*
import com.richarddewan.paging3_todo.data.local.entity.TaskEntity

@Dao
interface TaskFlowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(list:List<TaskEntity>)

    @Query("Select * from Tasks where id =:taskId")
    suspend fun getTask(taskId:Long)

    @Query("Select * From Tasks")
     fun getTasks():PagingSource<Int,TaskEntity>

     @Query("Delete From Tasks")
     suspend fun wipeTasks()

     @Delete()
     suspend fun deleteTask(task:TaskEntity)
}