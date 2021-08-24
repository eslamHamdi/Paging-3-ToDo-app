package com.eslamhamdi.paging3_todo.data.local.database

import androidx.paging.PagingSource
import androidx.room.*
import com.eslamhamdi.paging3_todo.data.local.entity.TaskEntity
import com.eslamhamdi.paging3_todo.domain.Task

@Dao
interface TaskFlowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(list:List<TaskEntity>)

    @Query("Select * from Tasks where id =:taskId")
    suspend fun getTask(taskId:Int) : TaskEntity

    @Query("Select * From Tasks Order By id Desc")
     fun getTasks():PagingSource<Int, TaskEntity>

     @Query("Delete From Tasks")
     suspend fun wipeTasks()

     @Delete()
     suspend fun deleteTask(task:TaskEntity)
}